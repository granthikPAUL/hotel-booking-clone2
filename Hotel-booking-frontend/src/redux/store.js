import { configureStore } from "@reduxjs/toolkit";
import globalReducer from "./reducers";
export const store = configureStore({
  reducer: { globalState: globalReducer },
});
