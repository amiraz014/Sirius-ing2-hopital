import { useContext } from "react";
import Epital from "../../Epital";
import MainMenu from "./Home/Components/mainMenu";
import AbsenceForm from "./AbsenceForm";

export default function AbsenceInterface(){  

        const con = useContext(Epital);

    return(
        
        <div class="flex flex-row justify-between bg-gray-900 min-h-screen">
            <div>{con.showMenu && <MainMenu/>}</div>
            <div class="flex flex-col items-center justify-center text-black">
                <AbsenceForm/>
            </div>
            <div></div>
        </div>
        
    );
}