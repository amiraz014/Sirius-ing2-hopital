import { useContext } from "react";
import Formular from "./components/Formular";
import Epital from "../Epital";
import MainMenu from "../Home/Components/mainMenu";

export default function FormPage(){
        const con = useContext(Epital);
    return(
        <>
        <div class="flex flex-row justify-between space-x-6 bg-gray-900">
        <div>
            {con.showMenu && <MainMenu/>}
        </div>

        <div className="flex flex-col items-center justify-center  text-white">
           <Formular/>
        </div>

        <div></div>
        </div>
        </>
    );
}