import { useEffect, useState } from "react";

const useHotelData = () => {
  const [hotelData, setHotelData] = useState([]);
  useEffect(() => {
    getHotelData();
  }, []);
  const getHotelData = async () => {
    try {
      const data = await fetch("http://localhost:8080/hotel/findAll");
      const response = await data.json();
      setHotelData(response);
    } catch (err) {
      console.log("error-->", err);
    }
  };

  return hotelData;
};

export default useHotelData;
