import { useEffect, useState } from "react";

const useHotelData = () => {
  const [hotelData, setHotelData] = useState([]);
  useEffect(() => {
    getHotelData();
  }, []);
  const getHotelData = async () => {
    const data = await fetch("http://localhost:8080/hotel/findAll");
    const response = await data.json();
    setHotelData(response);
  };

  return hotelData;
};

export default useHotelData;
