import React from "react";
import { MdLocalShipping } from "react-icons/md";
import { TbFreeRights } from "react-icons/tb";
import { GiPartyPopper } from "react-icons/gi";

const BannerBottom = () => {
  return (
    <div className="w-full bg-white border-b-[1px] py-4 border-b-gray-200 px-4">
      <div className="max-w-container mx-auto h-20 flex flex-col md:flex-row justify-between items-center">
        <div className="flex items-center gap-2 w-72 shadow-sm hover:shadow-md duration-300">
          <span className="text-xl text-center w-6 ml-1">
            <TbFreeRights />
          </span>
          <p className="text-lightText text-base">Free candles</p>
        </div>
        <div className="flex md:w-auto items-center gap-2 w-72 shadow-sm hover:shadow-md duration-300">
          <span className="text-xl text-center w-6 ml-1">
            <MdLocalShipping />
          </span>
          <p className="text-lightText text-base">Free shipping</p>
        </div>
        <div className="flex md:w-auto items-center gap-2 w-72 shadow-sm hover:shadow-md duration-300">
          <span className="text-2xl text-center w-6">
            <GiPartyPopper />
          </span>
          <p className="text-lightText text-base">Party organization</p>
        </div>
      </div>
    </div>
  );
};

export default BannerBottom;
