import AuthForm from "../components/AuthForm";
import {register} from "../api/api.js";
import { useNavigate } from "react-router-dom";

export default function RegisterPage(){
    const navigate = useNavigate();

    async function handleRegister(data){
        try{
            await register(data);
            alert("Registration successful! Please login.");
            navigate("/login");
        }
        catch(error){
            alert("Registration failed");
        }
    }

    return (
        <div>
            <AuthForm type="register" onSubmit={handleRegister} />
        </div>
    );
}
