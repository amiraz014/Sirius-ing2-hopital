import {createContext} from 'react';

import {useState} from 'react';

const Epital = createContext({
    showMenu: false,
    setShowMenu: () => {},
 });

 export const EpitalProvider = (props) => {
     const [showMenu, setShowMenu] = useState(false);
        return (
            <Epital.Provider value={{
                 showMenu: showMenu,
                 setShowMenu : setShowMenu,
                  }}>
                {props.children}
            </Epital.Provider>
        );
 };
    export default Epital;
 