import { Link } from "react-router-dom";
import { FaFacebookSquare } from "react-icons/fa";


export default function MainMenu(){

    return(
        <>
        <div class="bg-blue-400 rounded flex items-start justify-center min-h-screen">
        <nav class="bg-vlue-400 w-64">
        <ul class="space-y-2 p-4">
            <li class="group relative">
                <p class="block px-4 py-2 rounded-lg text-white hover:bg-blue-500 hover:text-white">Gestion du stock</p>
                <ul class="absolute left-full top-0 hidden group-hover:block bg-white shadow-lg rounded-lg w-40">
                    <li><Link to="/stock" class="block px-4 py-2 text-gray-700 hover:bg-blue-500 hover:text-white">Sortie du stock</Link></li>
                    <li><Link to="/" class="block px-4 py-2 text-gray-700 hover:bg-blue-500 hover:text-white">Entrée du stock</Link></li>
                </ul>
            </li>
            <li>
            <Link to="/" class="block px-4 py-2 text-white hover:bg-blue-500 hover:text-white">Historique</Link>
            </li>
            <li>
            <Link to="/" class="block px-4 py-2 text-white hover:bg-blue-500 hover:text-white">Alerte</Link>
            </li>
            <li>
            <Link to="/Manage" class="block px-4 py-2 text-white hover:bg-blue-500 hover:text-white">Gestion des employés</Link>     
            </li>
            <li class="flex flex-col">
                <span class="text-center">Nous-contactez !</span>
                <div class="mt-2 flex justify-center w-full bg-white text-black mx-auto">
                    <a class="m-1" href="https://www.facebook.com/"><FaFacebookSquare/></a>
                </div>


            </li>

        </ul>
    </nav>
        </div>
        
        </>

    );

}