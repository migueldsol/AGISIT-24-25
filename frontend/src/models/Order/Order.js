export default class Order {
    constructor(jsonObj) {
        if(jsonObj){
            this.orderId = jsonObj.orderId;
            this.userId = jsonObj.userId;
            this.orderDate = jsonObj.orderDate;
            this.totalPrice = jsonObj.totalPrice;
            this.ListOfProductIdas = jsonObj.ListOfProductIdas;
        }
    }
}
