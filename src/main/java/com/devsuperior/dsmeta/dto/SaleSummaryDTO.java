package com.devsuperior.dsmeta.dto;

public class SaleSummaryDTO {

    private String sellerName;
    private Double total;

    public SaleSummaryDTO() {
    }

    public SaleSummaryDTO(Double total, String sellerName) {
        this.total = total;
        this.sellerName = sellerName;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

}
