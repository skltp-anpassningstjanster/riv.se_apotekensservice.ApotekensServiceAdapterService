/**
 * Copyright (c) 2014 Inera AB, <http://inera.se/>
 * <p>
 * This file is part of SKLTP.
 * <p>
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * <p>
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * <p>
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
package se.skltp.adapterservices.apse.apsemedicalservicesadapteric.argos;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Class representing the Argos header information.
 */
public class ArgosHeader {

    private String forskrivarkod;
    private String legitimationskod;
    private String fornamn;
    private String efternamn;
    private String yrkesgrupp;
    private String befattningskod;
    private String arbetsplatskod;
    private String arbetsplatsnamn;
    private String postort;
    private String postadress;
    private String postnummer;
    private String telefonnummer;
    private String requestId;
    private String rollnamn;
    private String directoryID;
    private String hsaID;
    private String katalog;
    private String organisationsnummer;
    private String systemnamn;
    private String systemversion;
    private String systemIp;

    public ArgosHeader() {
    }

    public ArgosHeader(String forskrivarkod, String legitimationskod, String fornamn, String efternamn,
                       String yrkesgrupp, String befattningskod, String arbetsplatskod, String arbetsplatsnamn, String postort,
                       String postadress, String postnummer, String telefonnummer, String requestId, String rollnamn,
                       String directoryID, String hsaID, String katalog, String organisationsnummer, String systemnamn,
                       String systemversion, String systemIp) {

        this.forskrivarkod = forskrivarkod;
        this.legitimationskod = legitimationskod;
        this.fornamn = fornamn;
        this.efternamn = efternamn;
        this.yrkesgrupp = yrkesgrupp;
        this.befattningskod = befattningskod;
        this.arbetsplatskod = arbetsplatskod;
        this.arbetsplatsnamn = arbetsplatsnamn;
        this.postort = postort;
        this.postadress = postadress;
        this.postnummer = postnummer;
        this.telefonnummer = telefonnummer;
        this.requestId = requestId;
        this.rollnamn = rollnamn;
        this.directoryID = directoryID;
        this.hsaID = hsaID;
        this.katalog = katalog;
        this.organisationsnummer = organisationsnummer;
        this.systemnamn = systemnamn;
        this.systemversion = systemversion;
        this.systemIp = systemIp;
    }

    public void setYrkesGrupp(String yrkesgrupp) {
        this.yrkesgrupp = yrkesgrupp;
    }

    public String getForskrivarkod() {
        return forskrivarkod;
    }

    public void setForskrivarkod(String forskrivarkod) {
        this.forskrivarkod = forskrivarkod;
    }

    public String getLegitimationskod() {
        return legitimationskod;
    }

    public void setLegitimationskod(String legitimationskod) {
        this.legitimationskod = legitimationskod;
    }

    public String getFornamn() {
        return fornamn;
    }

    public void setFornamn(String fornamn) {
        this.fornamn = fornamn;
    }

    public String getEfternamn() {
        return efternamn;
    }

    public void setEfternamn(String efternamn) {
        this.efternamn = efternamn;
    }

    public String getYrkesgrupp() {
        return yrkesgrupp;
    }

    public String getBefattningskod() {
        return befattningskod;
    }

    public void setBefattningskod(String befattningskod) {
        this.befattningskod = befattningskod;
    }

    public String getArbetsplatskod() {
        return arbetsplatskod;
    }

    public void setArbetsplatskod(String arbetsplatskod) {
        this.arbetsplatskod = arbetsplatskod;
    }

    public String getArbetsplatsnamn() {
        return arbetsplatsnamn;
    }

    public void setArbetsplatsnamn(String arbetsplatsnamn) {
        this.arbetsplatsnamn = arbetsplatsnamn;
    }

    public String getPostort() {
        return postort;
    }

    public void setPostort(String postort) {
        this.postort = postort;
    }

    public String getPostadress() {
        return postadress;
    }

    public void setPostadress(String postadress) {
        this.postadress = postadress;
    }

    public String getPostnummer() {
        return postnummer;
    }

    public void setPostnummer(String postnummer) {
        this.postnummer = postnummer;
    }

    public String getTelefonnummer() {
        return telefonnummer;
    }

    public void setTelefonnummer(String telefonnummer) {
        this.telefonnummer = telefonnummer;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getRollnamn() {
        return rollnamn;
    }

    public void setRollnamn(String rollnamn) {
        this.rollnamn = rollnamn;
    }

    public String getDirectoryID() {
        return directoryID;
    }

    public void setDirectoryID(String directoryID) {
        this.directoryID = directoryID;
    }

    public String getHsaID() {
        return hsaID;
    }

    public void setHsaID(String hsaID) {
        this.hsaID = hsaID;
    }

    public String getKatalog() {
        return katalog;
    }

    public void setKatalog(String katalog) {
        this.katalog = katalog;
    }

    public String getOrganisationsnummer() {
        return organisationsnummer;
    }

    public void setOrganisationsnummer(String organisationsnummer) {
        this.organisationsnummer = organisationsnummer;
    }

    public String getSystemnamn() {
        return systemnamn;
    }

    public void setSystemnamn(String systemnamn) {
        this.systemnamn = systemnamn;
    }

    public String getSystemversion() {
        return systemversion;
    }

    public void setSystemversion(String systemversion) {
        this.systemversion = systemversion;
    }

    public String getSystemIp() {
        return systemIp;
    }

    public void setSystemIp(String systemIp) {
        this.systemIp = systemIp;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
