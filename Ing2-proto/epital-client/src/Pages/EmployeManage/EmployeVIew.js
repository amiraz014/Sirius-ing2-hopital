import { useContext } from "react";
import Epital from "../Epital";
import MainMenu from "../Home/Components/mainMenu";
import EmployeCard from "./components/EmployeCard";


export default function EmployeVIew(){

    const con = useContext(Epital);
    return(
        <>
        <div class="flex flex-row justify-between items-center bg-gray-900 min-h-screen"> 
            <div>{con.showMenu && <MainMenu/>}</div>

            <div><EmployeCard /></div>

            <div></div>
        </div>
        
        </>

    );
}