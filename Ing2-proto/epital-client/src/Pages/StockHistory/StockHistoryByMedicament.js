import React, { useEffect, useState, useContext } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';
import Epital from '../Epital';
import MainMenu from '../Home/Components/MainMenu';
import logo from '../EmployeManage/components/Epitalogo.svg'; // Ajout du logo si tu veux aussi l'inclure

const StockHistoryByMedicament = () => {
    const [medicaments, setMedicaments] = useState([]);
    const con = useContext(Epital);

    useEffect(() => {
        const fetchHistoriqueParMedicament = async () => {
            try {
                const response = await axios.get('http://172.31.252.28:8089/stock/historique/par-medicament');
                setMedicaments(response.data); 
            } catch (error) {
                console.error("Erreur lors de la récupération de l'historique par médicament", error);
            }
        };

        fetchHistoriqueParMedicament();
    }, []);

    return (
        <div className="flex bg-gray-900 text-white min-h-screen">
            {con.showMenu && <MainMenu />}
            <div className="flex-1 p-8 flex justify-center">
                <div className="w-full max-w-6xl bg-white text-gray-800 rounded-2xl shadow-lg p-6">
                    
                    
                    <div className="flex items-center gap-4 mb-6">
                        <img src={logo} alt="Epital Logo" className="h-10 w-auto" />
                        <h2 className="text-2xl font-bold">Historique des Sorties par Médicament</h2>
                    </div>

                  
                    <div className="overflow-x-auto">
                        <table className="min-w-full divide-y divide-gray-300">
                            <thead className="bg-gray-100 text-gray-700">
                                <tr>
                                    <th className="px-6 py-3 text-left text-sm font-semibold uppercase">ID Médicament</th>
                                    <th className="px-6 py-3 text-left text-sm font-semibold uppercase">Nom</th>
                                    <th className="px-6 py-3 text-left text-sm font-semibold uppercase">Quantité Totale Sortie</th>
                                    <th className="px-6 py-3 text-left text-sm font-semibold uppercase">Dernière Sortie</th>
                                    <th className="px-6 py-3 text-left text-sm font-semibold uppercase">Actions</th>
                                </tr>
                            </thead>
                            <tbody className="divide-y divide-gray-200">
                                {medicaments.map((medicament) => (
                                    <tr key={medicament.idMedicament} className="hover:bg-gray-50 transition duration-200">
                                        <td className="px-6 py-4">{medicament.idMedicament}</td>
                                        <td className="px-6 py-4 font-medium">{medicament.nomMedicament}</td>
                                        <td className="px-6 py-4">{medicament.quantiteTotaleSortie}</td>
                                        <td className="px-6 py-4">{new Date(medicament.derniereSortie).toLocaleDateString()}</td>
                                        <td className="px-6 py-4">
                                            <Link
                                                to={`/stock-history/medicament/${medicament.idMedicament}`}
                                                className="bg-blue-500 hover:bg-blue-600 text-white px-4 py-2 rounded-lg text-sm"
                                            >
                                                Voir détails
                                            </Link>
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

export default StockHistoryByMedicament;
