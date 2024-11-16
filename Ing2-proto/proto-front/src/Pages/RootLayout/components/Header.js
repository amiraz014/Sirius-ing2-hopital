import { Link } from "react-router-dom";
import { FaHome } from "react-icons/fa";
import { FaAddressCard } from "react-icons/fa";
import { FaUsersLine } from "react-icons/fa6";


export default function Header(props){

    

    return (
        <>
        <div className="flex flex-row h-16 content-start space-x-96 bg-blue-400">
            {/* Logo Part */}
            <div className="flex flex-row space-x-4">
                <img src={props.logo} className="h-16 w-auto md:h-16 md:w-auto object-contain"/>
            <span className="font-cursive text-2xl text-white flex items-center">{props.logotitle}</span>
            </div>

            {/* Items Part */}
            <div className="flex flex-row space-x-4">
            <Link to='/' className="flex items-center space-x-0.5 border-collapse">
            <FaHome/>
            <span className="font-serif text-black flex items-center ">{props.finame}</span>
            </Link>
            <Link to='/form' className="flex items-center space-x-0.5 border-collapse">
            <FaAddressCard/>
            <span className="font-serif text-black flex items-center ">{props.siname}</span>
            </Link>
            <Link to='/list' className="flex items-center space-x-0.5 border-collapse" >
            <FaUsersLine/>
            <span className="font-serif text-black flex items-center ">{props.tiname}</span>
            </Link>

            </div>
          
            

        </div>
        </>
    );

}