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
package se.skltp.adapterservices.apse.apsemedicalservicesadapteric.exception;

/**
 * Indicates there was some trouble generating ticket from ticket machine.
 */
public class TicketMachineException extends Exception {

    private static final long serialVersionUID = 1L;

    public TicketMachineException() {
        super("Ticket machine exception occured!");
    }

    public TicketMachineException(String msg) {
        super(msg);
    }

    public TicketMachineException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
