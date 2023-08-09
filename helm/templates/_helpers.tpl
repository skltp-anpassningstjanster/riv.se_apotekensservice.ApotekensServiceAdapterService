{{/*
Expand the name of the chart.
*/}}
{{- define "apse-adapter.name" -}}
{{- default .Chart.Name .Values.nameOverride | trunc 63 | trimSuffix "-" }}
{{- end }}

{{/*
Create a default fully qualified app name.
We truncate at 63 chars because some Kubernetes name fields are limited to this (by the DNS naming spec).
If release name contains chart name it will be used as a full name.
*/}}
{{- define "apse-adapter.fullname" -}}
{{- if .Values.fullnameOverride }}
{{- .Values.fullnameOverride | trunc 63 | trimSuffix "-" }}
{{- else }}
{{- $name := default .Chart.Name .Values.nameOverride }}
{{- if contains $name .Release.Name }}
{{- .Release.Name | trunc 63 | trimSuffix "-" }}
{{- else }}
{{- printf "%s-%s" .Release.Name $name | trunc 63 | trimSuffix "-" }}
{{- end }}
{{- end }}
{{- end }}

{{/*
Create chart name and version as used by the chart label.
*/}}
{{- define "apse-adapter.chart" -}}
{{- printf "%s-%s" .Chart.Name .Chart.Version | replace "+" "_" | trunc 63 | trimSuffix "-" }}
{{- end }}

{{/*
Common labels
*/}}
{{- define "apse-adapter.labels" -}}
helm.sh/chart: {{ include "apse-adapter.chart" . }}
{{ include "apse-adapter.selectorLabels" . }}
{{- if .Chart.AppVersion }}
app.kubernetes.io/version: {{ .Chart.AppVersion | quote }}
{{- end }}
app.kubernetes.io/managed-by: {{ .Release.Service }}
{{- end }}

{{/*
Selector labels
*/}}
{{- define "apse-adapter.selectorLabels" -}}
app.kubernetes.io/name: {{ include "apse-adapter.name" . }}
app.kubernetes.io/instance: {{ .Release.Name }}
{{- end }}

{{/*
Create the name of the service account to use
*/}}
{{- define "apse-adapter.serviceAccountName" -}}
{{- if .Values.serviceAccount.create }}
{{- default (include "apse-adapter.fullname" .) .Values.serviceAccount.name }}
{{- else }}
{{- default "default" .Values.serviceAccount.name }}
{{- end }}
{{- end }}



{{- define "recurseFlattenMapEnv" -}}
  {{- $map := first . -}}
  {{- $label := last . | default "" -}}
  {{- range $key, $val := $map -}}
    {{- $sublabel := without (list $label $key) ""  | join "_" | upper -}}
    {{- if kindIs "map" $val -}}
      {{- list $val $sublabel | include "recurseFlattenMapEnv" -}}
    {{- else if kindIs "slice" $val }}
{{ $sublabel }}: >-
      {{ range $val -}}
{{- .opt -}}{{- if .value -}}={{- .value -}}{{- end }}
      {{ end -}}
    {{- else }}
{{ $sublabel }}: {{ $val | quote -}}
    {{ end -}}
  {{- end -}}
{{- end -}}

{{- define "recurseFlattenMap" -}}
  {{- $map := first . -}}
  {{- $label := last . | default "" -}}
  {{- range $key, $val := $map -}}
    {{- $sublabel := without (list $label $key) ""  | join "." -}}
    {{- if kindOf $val | eq "map" -}}
      {{- list $val $sublabel | include "recurseFlattenMap" -}}
    {{- else -}}
      {{ $sublabel }}={{ $val | quote }}
    {{ end -}}
  {{- end -}}
{{- end -}}