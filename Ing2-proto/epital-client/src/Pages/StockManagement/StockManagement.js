import React, { useState, useEffect, useContext } from 'react';
import axios from 'axios';
import Epital from '../Epital';
import MainMenu from '../Home/Components/MainMenu';
import logo from '../EmployeManage/components/Epitalogo.svg'; // Logo Epital

const StockManagement = () => {
    const [sorties, setSorties] = useState([]);
    const [isRunning, setIsRunning] = useState(false); 
    const [error, setError] = useState(null); 
    const con = useContext(Epital);

    const fetchSorties = async () => {
        try {
            const response = await axios.get('http://172.31.252.28:8089/stock/sorties');
            setSorties(response.data); 
            setError(null);
        } catch (error) {
            console.error('Erreur lors de la récupération des sorties', error);
            setError('Erreur lors de la récupération des sorties');
        }
    };

    const handleStartSorties = async () => {
        try {
            await axios.get('http://172.31.252.28:8089/stock/lancer-sorties');
            setIsRunning(true);
            setError(null);
        } catch (error) {
            console.error('Erreur lors du démarrage des sorties automatiques', error);
            setError('Erreur lors du démarrage des sorties automatiques');
        }
    };

    const handleStopSorties = () => {
        setIsRunning(false);
    };

    useEffect(() => {
        let interval;
        if (isRunning) {
            interval = setInterval(fetchSorties, 2000);
        }
        return () => clearInterval(interval);
    }, [isRunning]);

    return (
        <div className="flex bg-gray-900 text-white min-h-screen">
            {con.showMenu && <MainMenu />}
            <div className="flex-1 flex justify-center p-8">
                <div className="w-full max-w-3xl bg-white text-gray-900 rounded-2xl shadow-lg p-6 space-y-6">
                    
                   
                    <div className="flex items-center gap-4">
                        <img src={logo} alt="Logo" className="h-10 w-auto" />
                        <h2 className="text-2xl font-bold">Sortie Automatique de Stock</h2>
                    </div>

                  
                    <div className="flex space-x-4">
                        <button
                            onClick={handleStartSorties}
                            className="w-full bg-blue-600 hover:bg-blue-700 text-white font-semibold py-2 px-4 rounded transition duration-300"
                            disabled={isRunning}
                        >
                            Démarrer les Sorties 
                        </button>
                        <button
                            onClick={handleStopSorties}
                            className="w-full bg-red-600 hover:bg-red-700 text-white font-semibold py-2 px-4 rounded transition duration-300"
                            disabled={!isRunning}
                        >
                            Arrêter les Sorties 
                        </button>
                    </div>

               
                    {error && (
                        <p className="text-red-600 text-center">{error}</p>
                    )}

                    <div>
                        <h3 className="text-xl font-semibold mb-3">Sorties effectuées :</h3>
                        <ul className="space-y-2 max-h-[300px] overflow-y-auto pr-2">
                            {sorties.map((sortie, index) => (
                                <li
                                    key={index}
                                    className={`p-3 rounded-lg shadow-sm ${
                                        sortie.startsWith("🔴") ? "bg-red-100 text-red-900" :
                                        sortie.startsWith("⚠️") ? "bg-yellow-100 text-yellow-900" :
                                        "bg-gray-100 text-gray-800"
                                    }`}
                                >
                                    {sortie}
                                </li>
                            ))}
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default StockManagement;
