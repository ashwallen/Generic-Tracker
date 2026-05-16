import AuthForm from "../components/AuthForm";
import {login} from "../api/api.js";
import { setToken } from "../utils/token";
import { useNavigate } from "react-router-dom";

export default function LoginPage(){
  const navigate = useNavigate();
  async function handleLogin(data){
    try {
      const result = await login(data);
      console.log("Login response:", result);
      const token = result.token || result.data;
      setToken(token);
      navigate("/dashboard");
    } catch (error) {
      alert("Invalid credentials");
    }
  }

  return (
    <div>
      <AuthForm type="login" onSubmit={handleLogin} />
    </div>
  );
}