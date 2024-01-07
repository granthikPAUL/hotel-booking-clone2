import { BiSolidMoon } from "react-icons/bi";
import { BsTagFill } from "react-icons/bs";
import { FaCalendarAlt } from "react-icons/fa";
import "./index.scss";

const Banner = () => {
  return (
    <div className="banner">
      <div>
        <h3>Find and book your perfect stay</h3>
      </div>
      <div className="banner-category">
        <BiSolidMoon />
        <p>Earn rewards on every night you stay</p>
      </div>
      <div className="banner-category">
        <BsTagFill />
        <p>Save more with Member Prices</p>
      </div>
      <div className="banner-category">
        <FaCalendarAlt />
        <p>Free cancellation options if plans change</p>
      </div>
    </div>
  );
};

export default Banner;
