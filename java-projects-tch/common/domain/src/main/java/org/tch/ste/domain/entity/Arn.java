package org.tch.ste.domain.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * ARN class has One To Many relationship with PaymentInstrument Class and
 * PermittedTokenRequestorArn Class.
 * 
 * @author sharduls
 * 
 */
@Entity
@Table(name = "ARNS")
public class Arn implements Serializable {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = -6368002991319356145L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private int id;

    @Column(name = "ARN")
    private String arn;

    @OneToMany(mappedBy = "arn")
    private List<PaymentInstrument> paymentInstruments;

    @OneToMany(mappedBy = "arn")
    private List<PermittedTokenRequestorArn> permittedTokenRequestorArns;

    /**
     * @return arn - An ARN
     */
    public String getArn() {
        return arn;
    }

    /**
     * @return id Integer - Unique ID for
     */
    public int getId() {
        return id;
    }

    /**
     * @return List of PaymentInstrument.
     */
    public List<PaymentInstrument> getPaymentInstruments() {
        return paymentInstruments;
    }

    /**
     * @return List of PermittedTokenRequestorArn.
     */
    public List<PermittedTokenRequestorArn> getPermittedTokenRequestorArns() {
        return permittedTokenRequestorArns;
    }

    /**
     * @param arn
     *            - Set the arn.
     */
    public void setArn(String arn) {
        this.arn = arn;
    }

    /**
     * @param id
     *            - Set the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @param paymentInstruments
     *            - Set the paymentInstruments.
     */
    public void setPaymentInstruments(List<PaymentInstrument> paymentInstruments) {
        this.paymentInstruments = paymentInstruments;
    }

    /**
     * @param permittedTokenRequestorArns
     *            - Set the permittedTokenRequestorArns.
     */
    public void setPermittedTokenRequestorArns(List<PermittedTokenRequestorArn> permittedTokenRequestorArns) {
        this.permittedTokenRequestorArns = permittedTokenRequestorArns;

    }
}
