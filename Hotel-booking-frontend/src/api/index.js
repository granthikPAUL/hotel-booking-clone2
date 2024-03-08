const baseUrl = "http://localhost:8080";
import axios from "axios";

export const RegistraionApi = async (data = {}) => {
  console.log("data inside RegistrationApi-->", data);
  try {
    const response = await fetch(baseUrl + "/saveUser", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(data),
    });
    console.log("respone-->", response);
    return response;
  } catch (err) {
    console.log("RegistraionApi error->", err);
  }
};

// export const LoginApi = async (data = { email: "", password: "" }) => {
//   console.log("data inside LoginApi-->", data);
//   try {
//     const response = await fetch(baseUrl + "/user/authenticate", {
//       method: "GET",
//       headers: {
//         Authorization: "Basic " + btoa(data.email + ":" + data.password),
//         "Content-Type": "text/plain",
//       },
//     });
//     console.log("respone-->", response.headers);
//     if (response.ok) {
//       const jwt = response.headers.get("Jwt");
//       console.log("JWT Token:", jwt);
//       return jwt;
//     } else {
//       throw new Error("Authentication failed");
//     }
//   } catch (err) {
//     console.log("LoginApi error->", err);
//   }
// };

export const LoginApi = async (data = { email: "", password: "" }) => {
  console.log("data inside LoginApi-->", data);
  try {
    const response = await axios.get(baseUrl + "/user/authenticate", {
      headers: {
        Authorization: "Basic " + btoa(data.email + ":" + data.password),
        "Content-Type": "application/json",
      },
    });

    // Access the Authorization header from the response headers
    const authorizationHeader = response.headers["Authorization"];

    console.log("Authorization Header:", authorizationHeader);

    console.log("response-->", response);
  } catch (err) {
    console.log("error ->", err);
  }
};
