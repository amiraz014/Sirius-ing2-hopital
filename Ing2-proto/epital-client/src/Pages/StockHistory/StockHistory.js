import React, { useEffect, useState, useContext } from 'react';
import axios from 'axios';
import Epital from '../Epital';
import MainMenu from '../Home/Components/MainMenu';
import logo from '../EmployeManage/components/Epitalogo.svg';

const StockHistory = () => {
    const [historique, setHistorique] = useState([]);
    const con = useContext(Epital);

    useEffect(() => {
        const fetchHistorique = async () => {
            try {
                const response = await axios.get('http://172.31.252.28:8089/stock/historique');
                setHistorique(response.data);
            } catch (error) {
                console.error('Erreur lors de la récupération de l\'historique', error);
            }
        };

        fetchHistorique();
    }, []);

    return (
        <div className="flex bg-gray-900 text-white min-h-screen">
            {con.showMenu && <MainMenu />}
            <div className="flex-1 p-8 flex justify-center">
                <div className="w-full max-w-6xl bg-white text-gray-800 rounded-2xl shadow-lg p-6">
                    
                   
                    <div className="flex items-center gap-4 mb-6">
                        <img src={logo} alt="Epital Logo" className="h-10 w-auto" />
                        <h2 className="text-2xl font-bold">Historique des Mouvements de Stock</h2>
                    </div>

                    {/* Tableau */}
                    <div className="overflow-x-auto">
                        <table className="min-w-full divide-y divide-gray-300">
                            <thead className="bg-gray-100 text-gray-700">
                                <tr>
                                    <th className="px-6 py-3 text-left text-sm font-semibold uppercase tracking-wider">ID Médicament</th>
                                    <th className="px-6 py-3 text-left text-sm font-semibold uppercase tracking-wider">Date</th>
                                    <th className="px-6 py-3 text-left text-sm font-semibold uppercase tracking-wider">Nom</th>
                                    <th className="px-6 py-3 text-left text-sm font-semibold uppercase tracking-wider">Quantité Sortie</th>
                                    <th className="px-6 py-3 text-left text-sm font-semibold uppercase tracking-wider">Type</th>
                                </tr>
                            </thead>
                            <tbody className="divide-y divide-gray-200">
                                {historique.map((mouvement) => (
                                    <tr key={mouvement.idh} className="hover:bg-gray-50 transition duration-200">
                                        <td className="px-6 py-4">{mouvement.medicament.idm}</td>
                                        <td className="px-6 py-4">{new Date(mouvement.date_mouvement).toLocaleDateString()}</td>
                                        <td className="px-6 py-4 font-medium">{mouvement.medicament.nom}</td>
                                        <td className="px-6 py-4">{mouvement.quantite}</td>
                                        <td className="px-6 py-4">
                                            <span
                                                className={`inline-block px-3 py-1 text-xs font-bold rounded-full ${
                                                    mouvement.type === 'SORTIE'
                                                        ? 'bg-red-500 text-white'
                                                        : 'bg-green-500 text-white'
                                                }`}
                                            >
                                                {mouvement.type}
                                            </span>
                                        </td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    </div>

                </div>
            </div>
        </div>
    );
};

export default StockHistory;
