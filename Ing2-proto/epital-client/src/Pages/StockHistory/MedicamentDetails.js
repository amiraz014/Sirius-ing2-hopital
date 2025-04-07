import React, { useEffect, useState, useContext } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';
import Epital from '../Epital';
import MainMenu from '../Home/Components/MainMenu';
import logo from '../EmployeManage/components/Epitalogo.svg'; // logo épital

const MedicamentDetails = () => {
    const { id } = useParams();
    const [medicament, setMedicament] = useState(null);
    const [sorties, setSorties] = useState([]);
    const con = useContext(Epital);

    useEffect(() => {
        const fetchMedicamentDetails = async () => {
            try {
                const responseMedicament = await axios.get(`http://172.31.252.28:8089/stock/historique/par-medicament`);
                const medicamentData = responseMedicament.data.find((m) => m.idMedicament === parseInt(id));
                setMedicament(medicamentData);

                const responseSorties = await axios.get(`http://172.31.252.28:8089/stock/historique`);
                const sortiesData = responseSorties.data.filter((s) => s.medicament.idm === parseInt(id));
                setSorties(sortiesData);
            } catch (error) {
                console.error('Erreur lors de la récupération des détails du médicament', error);
            }
        };

        fetchMedicamentDetails();
    }, [id]);

    if (!medicament) {
        return <div className="p-8 bg-gray-900 text-white min-h-screen">Chargement...</div>;
    }

    return (
        <div className="flex bg-gray-900 text-white min-h-screen">
            {con.showMenu && <MainMenu />}
            <div className="flex-1 p-8 flex justify-center">
                <div className="w-full max-w-4xl bg-white text-gray-800 rounded-2xl shadow-lg p-6 space-y-6">
                    
                  
                    <div className="flex items-center gap-4">
                        <img src={logo} alt="Logo" className="h-10 w-auto" />
                        <h2 className="text-2xl font-bold">Détails du Médicament</h2>
                    </div>

                   
                    <div className="space-y-2">
                        <p><strong>Nom du médicament :</strong> {medicament.nomMedicament}</p>
                        <p><strong>ID du médicament :</strong> {medicament.idMedicament}</p>
                        <p><strong>Quantité totale sortie :</strong> {medicament.quantiteTotaleSortie} unités</p>
                    </div>

                   
                    <div>
                        <h3 className="text-xl font-semibold mb-4 mt-6">Liste des sorties :</h3>
                        <ul className="space-y-3">
                            {sorties.map((sortie, index) => (
                                <li key={sortie.idh} className="bg-gray-100 p-4 rounded-lg shadow-sm">
                                    <p><strong>Sortie {index + 1} :</strong> {sortie.quantite} unités</p>
                                    <p><strong>Date :</strong> {new Date(sortie.date_mouvement).toLocaleDateString()}</p>
                                </li>
                            ))}
                        </ul>
                    </div>

                </div>
            </div>
        </div>
    );
};

export default MedicamentDetails;
