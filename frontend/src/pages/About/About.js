import React, { useEffect, useState } from "react";
import { Link, useLocation } from "react-router-dom";
import Breadcrumbs from "../../components/pageProps/Breadcrumbs";
import { logo } from "../../assets/images";
import Image from "../../components/designLayouts/Image";

const About = () => {
  const location = useLocation();
  const [prevLocation, setPrevLocation] = useState("");
  useEffect(() => {
    setPrevLocation(location.state.data);
  }, [location]);
  return (
    // div that surrounds a column with the content of the page
    <div className="flex justify-center items-center" >
      <div className="max-w-[1000px] grid grid-cols-3 gap-4 p-10">
        <div className=" col-span-2 max-w-container mx-auto px-4">
          <Breadcrumbs title="About" prevLocation={prevLocation} />
          <div className="pb-10">
            <h1 className="text-primeColor font-semibold text-lg">
              About Caseirinha Real
            </h1>
            <h2 className="max-w-[600px] text-base text-lightText mb-2">
              Founded in 2005, Caseirinha Real was born out of a passion for artisanal baking, growing over the years into a reference for personalized cakes. Each cake we create is made with high-quality ingredients and refined techniques, reflecting our commitment to excellence.            </h2>
            <h1 className="text-primeColor font-semibold text-lg">
              Creativity and excellence
            </h1>
            <h2 className="max-w-[600px] text-base text-lightText mb-2">
              At Caseirinha Real, we transform ideas into unique cakes. Our highly skilled team masters modern decoration techniques, creating cakes that range from simple to highly elaborate, always with a touch of artistry and creativity.            </h2>
            <h1 className="text-primeColor font-semibold text-lg">
              Festive Events
            </h1>
            <h2 className="max-w-[600px] text-base text-lightText mb-2">
              In addition to delicious cakes, we organize complete events for birthdays, weddings, gender reveals, and other celebrations. We personalize every detail to make your event truly unforgettable.            
            </h2>
            <h1 className="text-primeColor font-semibold text-lg">
              A step ahead in confectionery
            </h1>
            <h2 className="max-w-[600px] text-base text-lightText mb-2">
              With almost two decades of experience, we stand out for our innovation and quality. Each cake is a unique piece, designed to make your event special, with a team dedicated to transforming any special ocasion into a memorable experience.
            </h2>
            <br/>
            <Link to="/">
              <button className="w-52 h-10 bg-primeColor text-white hover:bg-black duration-300">
                Continue Shopping
              </button>
            </Link>
          </div>
        </div>
        <div className="flex justify-center items-center">
          <Image imgSrc={logo}/>
        </div>
      </div>
    </div>
  );
};

export default About;
