import axios from 'axios';

const httpProducts = axios.create();
httpProducts.defaults.timeout = 100000;
httpProducts.defaults.baseURL = process.env.REACT_APP_BACKEND_PRODUCTS_BASE_URL;

const httpShoppingCart = axios.create();
httpShoppingCart.defaults.timeout = 100000;
httpShoppingCart.defaults.baseURL = process.env.REACT_APP_BACKEND_SHOPPING_CART_BASE_URL;

const httpUsers = axios.create();
httpUsers.defaults.timeout = 100000;
httpUsers.defaults.baseURL = process.env.REACT_APP_BACKEND_USERS_BASE_URL;


console.error(process.env.REACT_APP_BACKEND_PRODUCTS_BASE_URL, process.env.REACT_APP_BACKEND_SHOPPING_CART_BASE_URL, process.env.REACT_APP_BACKEND_USERS_BASE_URL);

const RemoteServices = {
  httpProducts,
  httpShoppingCart,
  httpUsers
};

export const LOGIN_URL = '/auth/user';
export const SIGNUP_URL = '/user';
export const GETUSER_URL = '/user';

export const ORDER_URL = '/order';

export const PRODUCTS_URL = '/products';

export default RemoteServices;