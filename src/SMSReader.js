import { NativeModules } from "react-native";
const {SMSReader} = NativeModules;

export const getSMS = (callback) => {
    SMSReader.getSMS(callback);
  };
