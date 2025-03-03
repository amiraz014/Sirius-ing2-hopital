import { GiHamburgerMenu} from "react-icons/gi";
import { IoMdClose} from "react-icons/io";
import { useState } from "react";
import MainMenu from "./mainMenu";
    export default function HumbergerComponent(props){

        // Source : https://www.youtube.com/watch?v=1tcFPodwQx4
       
        const [openMenu, setOpenMenu] = useState(false);
        

        const openMenuHandler = () => {
            setOpenMenu((prevState) => {
              return !prevState;
            });
            props.setShowMenu((prevState) => {
                return !prevState;
              });
            };
            const menuIcon = openMenu ? <IoMdClose className="w-20 h-14  text-white"/> : <GiHamburgerMenu className="w-20 h-14 text-white"/> ;

        

        return(
        <>
        {/* Burger Menu Button */}
        <div className="flex bg-blue-400 justify-between h-14">
         <a
         className={
             "w-20 flex flex-col hover:cursor-pointer hover:bg-blue-500 hover:shadow-md transition " +
             (openMenu ? "bg-blue-500 border-b-4" : "")
         }
         onClick={openMenuHandler}
        >
        {menuIcon}
      </a>
      
      </div>
      
      </>
        );
    }
