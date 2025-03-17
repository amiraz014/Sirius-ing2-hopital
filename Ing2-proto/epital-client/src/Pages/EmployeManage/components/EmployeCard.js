import logo from './Epitalogo.svg';

export default function EmployeCard(props) {
    const employes = props.employes;
    
    
    
    return(
        <div>
          {/* mapping start */}
        {employes.map((item, index) => (
      <div key={index} class="mx-auto mt-4 flex h-50 w-fit flex-col border-b border-white">
        <span class="font-semibold font-xl px-4 py-0.5 bg-blue-400 text-white w-auto rounded-t-lg">{item.nom}</span>
        <div class="flex flex-col">
            <div class="flex bg-white rounded-r-lg ">
              <div class="flex h-20 w-20  pl-2">
                <img src={logo} alt="logo" className="h-12 my-auto"/>
              </div>
              <div class="flex flex-col pt-2 pr-2 mb-6 justify-center ">
                <span class="font-bold text-sm px-2 py-2 border-blue-400 bg-blue-400 text-white">
                  <span>Garde de sécurité</span>
                </span>
              </div> 
             {item.gardes.map((garde, index) => (
              <div key={index} class="flex flex-col space-y-0.5 border-2 pt-3">
                <span class="mx-auto text-center bg-blue-400 text-white w-auto rounded-t-lg px-2 text-xs"> 
                 {garde.type}
                </span>
                <span class="rounded-lg text-center bg-blue-400 text-white px-2 text-sm ">
                  {garde.date}
                </span>
                <span class="rounded-lg text-center bg-blue-400 text-white px-2 text-sm ">{garde.heure}</span>
                <span class="rounded-lg text-center bg-blue-400 text-white px-2 text-sm ">{garde.lieu.secteur}</span>
              </div>
              ))}
          </div>
            </div>
            
      </div>
    ))}
       {/* mapping end */}
    </div>
    );
}
