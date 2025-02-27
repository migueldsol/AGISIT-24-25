import React from "react";
import { useDispatch } from "react-redux";
import { addToCart } from "../../../redux/orebiSlice";

const ProductInfo = ({ productInfo }) => {
  const dispatch = useDispatch();

  // Ensure productInfo is defined before trying to access its properties
  if (!productInfo) return <div>No product information available.</div>;

  return (
    <div className="flex flex-col gap-5">
      <h2 className="text-4xl font-semibold">{productInfo.name}</h2>
      <p className="text-xl font-semibold">${productInfo.price}</p>
      <p className="text-base text-gray-600">{productInfo.description}</p>
      <p className="text-sm">Be the first to leave a review.</p>
      <p className="font-medium text-lg">
        {/*<span className="font-normal">Colors:</span> {productInfo.color}*/}
      </p>
      <button
        onClick={() =>
          dispatch(
            addToCart({
              _id: productInfo.id, // Ensure you're using the correct ID
              id: productInfo.id,
              name: productInfo.name,
              quantity: 1,
              image: 'ia_100000545.jpg',
              price: productInfo.price,
              //colors: productInfo.color,
            })
          )
        }
        className="w-full py-4 bg-primeColor hover:bg-black duration-300 text-white text-lg font-titleFont"
      >
        Add to Cart
      </button>
    </div>
  );
};

export default ProductInfo;