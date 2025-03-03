import { Link } from "react-router-dom";

import logo from './Epitalogo.svg';
import HumbergerComponent from "./HumbergerComponent";
export default function Header(props){



    return (
        <>
        <div class="flex flex-row bg-blue-400 justify-between h-14">
            {/* Burger Menu Button */}
                <HumbergerComponent/>


            {/* Logo Part */}
            <div className="flex flex-row" tabIndex={-1}> 
            <img src={logo} alt="logo" className="h-12 my-auto"/>
            <Link to ="/" className="text-white text-3xl   m-auto   font-cursive">{props.logotitle}</Link>
            </div>


            <div>
                {/**/}
            </div>
        </div>
        </>
    );

}