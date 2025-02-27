import React, { useEffect, useState } from "react";
import axios from "axios"; // Make sure axios is imported
import Breadcrumbs from "../../components/pageProps/Breadcrumbs";
import {cake} from "../../assets/images/index";
import ProductInfo from "../../components/pageProps/productDetails/ProductInfo";
import ProductsOnSale from "../../components/pageProps/productDetails/ProductsOnSale";

import { useLocation } from "react-router-dom";

const ProductDetails = () => {
  const _id = useLocation().state.item._id || ""; // Extract _id from the location state
  const [prevLocation, setPrevLocation] = useState("");
  const [productInfo, setProductInfo] = useState([]); // Use null initially
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  // Fetch the product data based on the _id
  useEffect(() => {
    const fetchProduct = async () => {
      try {
        const response = await axios.get("http://localhost:8082/products/" + _id); // Fetch product data based on _id
        setProductInfo(response.data);
        console.log(response.data)
        setLoading(false);
      } catch (err) {
        setError("Error fetching product details.");
        setLoading(false);
      }
    };

    fetchProduct();
    setPrevLocation(window.location.pathname); // Set the previous location
  }, [_id]);

  if (loading) return <div>Loading...</div>; // Show loading state
  if (error) return <div>{error}</div>; // Show error state
  if (!productInfo) return <div>No product found.</div>; // Handle case when no product is found

  return (
    <div className="w-full mx-auto border-b-[1px] border-b-gray-300">
      <div className="max-w-container mx-auto px-4">
        <div className="xl:-mt-10 -mt-7">
          <Breadcrumbs title="" prevLocation={prevLocation} />
        </div>
        <div className="w-full grid grid-cols-1 md:grid-cols-2 xl:grid-cols-6 gap-4 h-full -mt-5 xl:-mt-8 pb-10 bg-gray-100 p-4">
          <div className="h-full xl:col-span-2">
            <img
              className="w-full h-full object-cover"
              src={cake} // Use fetched product image
              alt={productInfo.name} // Use fetched product name for alt text
            />
          </div>
          <div className="h-full w-full md:col-span-2 xl:col-span-3 xl:p-14 flex flex-col gap-6 justify-center">
            <ProductInfo productInfo={productInfo} />
          </div>
        </div>
      </div>
    </div>
  );
};

export default ProductDetails;