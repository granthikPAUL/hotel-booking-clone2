import "./index.scss";
import useHotelData from "../../utils/useHotelData";
import HotelCard from "../hotelCard/HotelCard";

const HotelListing = ({ imgData }) => {
  const hotelData = useHotelData();

  return (
    <div className="hotel-listing">
      <h3>Get away this weekend</h3>
      <div className="hotel-cards">
        {hotelData.map((hotel, index) => {
          return <HotelCard img={imgData[index]} data={hotel} key={index} />;
        })}
      </div>
    </div>
  );
};

export default HotelListing;
