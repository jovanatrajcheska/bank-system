public class Transaction {
    private double amount;
    private String originId;
    private String resultId;
    private String reason;
    private TransferType transactionTransferType;
    private FeeType feeType;

    public Transaction(double amount, String originId, String resultId, String reason, TransferType transactionTransferType) {
        this.amount = amount;
        this.originId = originId;
        this.resultId = resultId;
        this.reason = reason;
        this.transactionTransferType = transactionTransferType;
        feeType = FeeType.PERCENT;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getOriginId() {
        return originId;
    }

    public void setOriginId(String originId) {
        this.originId = originId;
    }

    public String getResultId() {
        return resultId;
    }

    public void setResultId(String resultId) {
        this.resultId = resultId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public TransferType getTransactionType() {
        return transactionTransferType;
    }

    public void setTransactionType(TransferType transactionTransferType) {
        this.transactionTransferType = transactionTransferType;
    }

    public TransferType getTransactionTransferType() {
        return transactionTransferType;
    }

    public void setTransactionTransferType(TransferType transactionTransferType) {
        this.transactionTransferType = transactionTransferType;
    }

    public FeeType getFeeType() {
        return feeType;
    }

    public void setFeeType(FeeType feeType) {
        this.feeType = feeType;
    }

    @Override
    public String toString() {
        return "Transaction: " +
                "Amount: " + amount +
                ", Origin ID: " + originId  +
                ", Result ID: " + resultId  +
                ", Reason: " + reason  +
                ", Type: " + transactionTransferType;
    }
}
