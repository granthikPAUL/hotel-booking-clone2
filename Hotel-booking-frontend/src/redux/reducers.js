import { createSlice } from "@reduxjs/toolkit";

const initialState = {
  login: true,
  userInfo: {},
};

export const globalSlice = createSlice({
  name: "globalSlice",
  initialState,
  reducers: {
    loginState: (state, action) => {
      state.login = action.payload;
    },
  },
});

export const { loginState } = globalSlice.actions;
export default globalSlice.reducer;
