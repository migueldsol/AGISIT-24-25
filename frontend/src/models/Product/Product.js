export default class Product{
    constructor(jsonObj){
        if (jsonObj){
            this.id = jsonObj.id;
            this.name = jsonObj.name;
            this.description = jsonObj.description;
            this.price = jsonObj.price;
            this.quantity = jsonObj.quantity;
        }
    }
}