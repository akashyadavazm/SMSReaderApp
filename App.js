import { useState } from "react"
import { getSMS } from "./src/SMSReader";
import { Button } from "react-native";

const App = () => {
  const [sms, setSMS] = useState('');
  const fetchSMS = () =>{
    getSMS((smsList) =>{
      setSMS(smsList);
    });
  };
  return(
    <View>
      <Button title="Read SMS" onPress={fetchSMS}/>
      <Text>{sms}</Text>
    </View>
  )
}