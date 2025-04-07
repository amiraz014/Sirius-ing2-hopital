import { Link } from "react-router-dom";
import { FaFacebookSquare } from "react-icons/fa";
import { FaSquareInstagram } from "react-icons/fa6";
import { FaLinkedin } from "react-icons/fa6";
import { FaTwitter } from "react-icons/fa6";


export default function MainMenu() {
    return (
        <div className="bg-blue-400 rounded flex items-start justify-center min-h-screen">
            <nav className="bg-blue-400 w-64 space-y-52">
               
                <ul className="space-y-2 p-4">
                    
                    <li className="group relative">
                        <p className="block px-4 py-2 rounded-lg text-white hover:bg-blue-500 hover:text-white cursor-pointer">
                            Gestion du stock
                        </p>
                        <ul className="absolute left-full top-0 hidden group-hover:block bg-white shadow-lg rounded-lg w-40">
                            <li>
                                <Link
                                    to="/stock-management"
                                    className="block px-4 py-2 text-gray-700 hover:bg-blue-500 hover:text-white"
                                >
                                    Sortie du stock
                                </Link>
                            </li>
                            <li>
                                <Link
                                    to="/"
                                    className="block px-4 py-2 text-gray-700 hover:bg-blue-500 hover:text-white"
                                >
                                    Entrée du stock
                                </Link>
                            </li>
                        </ul>
                    </li>

                    
                    <li className="group relative">
                        <p className="block px-4 py-2 rounded-lg text-white hover:bg-blue-500 hover:text-white cursor-pointer">
                            Historique
                        </p>
                        <ul className="absolute left-full top-0 hidden group-hover:block bg-white shadow-lg rounded-lg w-40">
                            <li>
                                <Link
                                    to="/stock-history"
                                    className="block px-4 py-2 text-gray-700 hover:bg-blue-500 hover:text-white"
                                >
                                    Historique global
                                </Link>
                            </li>
                            <li>
                                <Link
                                    to="/stock-history/medicament"
                                    className="block px-4 py-2 text-gray-700 hover:bg-blue-500 hover:text-white"
                                >
                                    Historique par médicament
                                </Link>
                            </li>
                        </ul>
                    </li>

                   
                    <li>
                        <Link
                            to="/alertes"
                            className="block px-4 py-2 text-white hover:bg-blue-500 hover:text-white"
                        >
                            Alerte
                        </Link>
                    </li>

                   
                    <li>
                        <Link
                            to="/Manage"
                            className="block px-4 py-2 text-white hover:bg-blue-500 hover:text-white"
                        >
                            Gestion des employés
                        </Link>
                    </li>
                </ul>

              
                <ul className="space-y-2 p-4">
                    <li className="flex flex-col">
                        <span className="text-center text-white">Nous suivre !</span>
                        <div className="mt-2 flex justify-center w-full bg-white text-black mx-auto rounded-lg p-2">
                            <a className="m-1 hover:text-blue-500" href="https://www.facebook.com/">
                                <FaFacebookSquare />
                            </a>
                            <a className="m-1 hover:text-pink-500" href="https://www.instagram.com/">
                                <FaSquareInstagram />
                            </a>
                            <a className="m-1 hover:text-blue-700" href="https://www.linkedin.com/">
                                <FaLinkedin />
                            </a>
                            <a className="m-1 hover:text-blue-400" href="https://www.twitter.com/">
                                <FaTwitter />
                            </a>
                        </div>
                    </li>
                </ul>

               
                <ul className="space-y-2 p-4">
                    <li>
                        <p className="text-white text-3xl text-center font-cursive">EPITAL</p>
                        <p className="text-white italic text-center">@Copyright Epital</p>
                    </li>
                </ul>
            </nav>
        </div>
    );
}