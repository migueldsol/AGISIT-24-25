export default class AuthUser {
    id: Number;
    username !: String;

    constructor(jsonObj?: AuthUser) {
        if(jsonObj) {
            this.id = jsonObj.id;
            this.username = jsonObj.username;
        }
    }
}