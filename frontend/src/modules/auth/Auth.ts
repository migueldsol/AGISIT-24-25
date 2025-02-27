import User from '../user/User';

export default class Auth {
    token !: String;
    user !: User;
    
    constructor(jsonObj?: Auth) {
        if(jsonObj) {
            this.token = jsonObj.token;
            this.user = new User(jsonObj.user);
        }
    }
}