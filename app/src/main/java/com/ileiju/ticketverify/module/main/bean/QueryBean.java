package com.ileiju.ticketverify.module.main.bean;

import java.util.List;

/**
 * Created by hunter on 17/12/9.
 */

public class QueryBean {
    private String status;
    private OrderTemp orders;


    public class OrderTemp {
        private OrderBean orderKey;

        public OrderBean getOrderKey() {
            return orderKey;
        }

        public void setOrderKey(OrderBean orderKey) {
            this.orderKey = orderKey;
        }
    }

    public class OrderBean {

        private String ordernum;
        private String code;
        private String mcode;
        private String ptype;
        private String pmode;
        private String status;
        private String landid;
        private String series;
        private String endtime;
        private String ordertel;
        private String ordername;
        private String ordertime;
        private String begintime;
        private String paystatus;
        private String checktime;
        private String ifprint;
        private List<Tickets> tickets;
        private int onsale;
        private boolean isResource;

        public String getOrdernum() {
            return ordernum;
        }

        public void setOrdernum(String ordernum) {
            this.ordernum = ordernum;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMcode() {
            return mcode;
        }

        public void setMcode(String mcode) {
            this.mcode = mcode;
        }

        public String getPtype() {
            return ptype;
        }

        public void setPtype(String ptype) {
            this.ptype = ptype;
        }

        public String getPmode() {
            return pmode;
        }

        public void setPmode(String pmode) {
            this.pmode = pmode;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getLandid() {
            return landid;
        }

        public void setLandid(String landid) {
            this.landid = landid;
        }

        public String getSeries() {
            return series;
        }

        public void setSeries(String series) {
            this.series = series;
        }

        public String getEndtime() {
            return endtime;
        }

        public void setEndtime(String endtime) {
            this.endtime = endtime;
        }

        public String getOrdertel() {
            return ordertel;
        }

        public void setOrdertel(String ordertel) {
            this.ordertel = ordertel;
        }

        public String getOrdername() {
            return ordername;
        }

        public void setOrdername(String ordername) {
            this.ordername = ordername;
        }

        public String getOrdertime() {
            return ordertime;
        }

        public void setOrdertime(String ordertime) {
            this.ordertime = ordertime;
        }

        public String getBegintime() {
            return begintime;
        }

        public void setBegintime(String begintime) {
            this.begintime = begintime;
        }

        public String getPaystatus() {
            return paystatus;
        }

        public void setPaystatus(String paystatus) {
            this.paystatus = paystatus;
        }

        public String getChecktime() {
            return checktime;
        }

        public void setChecktime(String checktime) {
            this.checktime = checktime;
        }

        public String getIfprint() {
            return ifprint;
        }

        public void setIfprint(String ifprint) {
            this.ifprint = ifprint;
        }

        public List<Tickets> getTickets() {
            return tickets;
        }

        public void setTickets(List<Tickets> tickets) {
            this.tickets = tickets;
        }

        public int getOnsale() {
            return onsale;
        }

        public void setOnsale(int onsale) {
            this.onsale = onsale;
        }

        public boolean isResource() {
            return isResource;
        }

        public void setResource(boolean resource) {
            isResource = resource;
        }
    }

    public class Tickets {

        private int tid;
        private int tnum;
        private int tnum_s;
        private String name;
        private String tprice;
        private String ordernum;
        private String status;
        private String batch_check;
        private String refund_audit;
        private String revoke_audit;

        public int getTid() {
            return tid;
        }

        public void setTid(int tid) {
            this.tid = tid;
        }

        public int getTnum() {
            return tnum;
        }

        public void setTnum(int tnum) {
            this.tnum = tnum;
        }

        public int getTnum_s() {
            return tnum_s;
        }

        public void setTnum_s(int tnum_s) {
            this.tnum_s = tnum_s;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTprice() {
            return tprice;
        }

        public void setTprice(String tprice) {
            this.tprice = tprice;
        }

        public String getOrdernum() {
            return ordernum;
        }

        public void setOrdernum(String ordernum) {
            this.ordernum = ordernum;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getBatch_check() {
            return batch_check;
        }

        public void setBatch_check(String batch_check) {
            this.batch_check = batch_check;
        }

        public String getRefund_audit() {
            return refund_audit;
        }

        public void setRefund_audit(String refund_audit) {
            this.refund_audit = refund_audit;
        }

        public String getRevoke_audit() {
            return revoke_audit;
        }

        public void setRevoke_audit(String revoke_audit) {
            this.revoke_audit = revoke_audit;
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public OrderTemp getOrders() {
        return orders;
    }

    public void setOrders(OrderTemp orders) {
        this.orders = orders;
    }
}