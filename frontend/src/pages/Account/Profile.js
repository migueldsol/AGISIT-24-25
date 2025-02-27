import React, {useState, useEffect} from "react";
import { useDispatch, useSelector } from "react-redux";
import Breadcrumbs from "../../components/pageProps/Breadcrumbs";
import { useNavigate } from "react-router-dom";
import { setAuth } from "../../redux/orebiSlice";
import RemoteServices, { ORDER_URL } from "../../services/RemoveServices";

const Profile = () => {
  const auth = useSelector((state) => state.orebiReducer.auth);
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const [error, setError] = useState("");
  const [orders, setOrders] = useState([]);

  const httpClient = RemoteServices.httpShoppingCart

  const handleLogout = () => {
    dispatch(setAuth({ user: null, token: "" }));
    navigate("/signin");
  }

  const fetchProducts = async () => {
    try {
      const response = await httpClient.get(ORDER_URL + "/" + auth.user.id,
        {
          headers: { 
            "Content-Type": "application/json",
            Authorization: `Bearer ${auth.token}`,
            Username: auth.user.username
           },
          withCredentials: true,
        }
      );
      console.log(response.data);
      setOrders(response.data);
    } catch (err) {
        setError("Error fetching orders.");
    }
  }

  console.log(auth);

  useEffect(() => {
    if (auth.user) {
      fetchProducts();
    }
  }, []);

  return (
    <div className="max-w-container mx-auto px-4">
      <Breadcrumbs title="Profile" />
      <div className="w-full h-full flex flex-col gap-10 pb-20">
        <div className="bg-white p-6 rounded-md shadow-md">
          <h2 className="text-2xl font-semibold mb-4">Profile Information</h2>
          {auth ? (
            <div>
              <p className="text-lg">
                <strong>Username:</strong> {auth.user.username}
              </p>
              <p className="text-lg">
                <strong>Email:</strong> {auth.user.email}
              </p>
            </div>
          ) : (
            <p className="text-lg">Please sign in to view your profile.</p>
          )}
        </div>
        <div className="bg-white p-6 rounded-md shadow-md">
          <h2 className="text-2xl font-semibold mb-4">Your Orders</h2>
          {orders && orders.length > 0 ? (
            <ul className="flex flex-col gap-4">
              {orders.map((order) => (
                <li key={order.id} className="border-b pb-4">
                  <p className="text-lg">
                    <strong>Order ID:</strong> {order.orderId}
                  </p>
                  <p className="text-lg">
                    <strong>Date:</strong> {order.orderDate[0]+ " " + order.orderDate[1] + " " + order.orderDate[2]}
                  </p>
                  <p className="text-lg">
                    <strong>Total:</strong> ${order.totalPrice}
                  </p>
                </li>
              ))}
            </ul>
          ) : (
            <p className="text-lg">You have no orders.</p>
          )}
          {error && <p className="text-red-500">{error}</p>}
        </div>
      </div>
          <button 
            onClick={handleLogout} 
            className="bg-red-500 text-white px-4 py-2 rounded-md my-4"
          >
            Logout
          </button>
    </div>
  );
};

export default Profile;