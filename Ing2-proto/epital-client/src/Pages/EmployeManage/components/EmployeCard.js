import logo from './Epitalogo.svg';

export default function EmployeCard(props){

    return(
        <div>
      <div class="mx-auto mt-4 flex h-50 w-fit flex-col border-b border-white">
        <span class="font-semibold font-xl px-4 py-0.5 bg-blue-400 text-white w-auto rounded-t-lg">employe name</span>
        <div class="flex flex-col">
            <div class="flex bg-white rounded-r-lg ">
              <div class="flex h-20 w-20  pl-2">
                <img src={logo} alt="logo" className="h-12 my-auto"/>
              </div>
              <div class="flex flex-col pt-2 pr-2">
                <span class="font-bold text-sm px-2  rounded-t-lg  border-blue-400 bg-blue-400 text-white">
                  <span><h1 class="italic text-white">profession name</h1></span>
                </span>
                <span class="flex text-sm border-2 border-blue-400  justify-center font-semibold p-0.5 ">
                  <h1>Zone</h1>
                </span>
              </div>
              <div class="flex flex-col border-2 pt-3">
                <span class="mx-auto text-center bg-blue-400 text-white w-auto rounded-t-lg px-2 text-xs"> 
                  <h1>gard type</h1>
                </span>
                <span class="rounded-lg text-center bg-blue-400 text-white px-2 text-sm ">
                  <h1>gard date</h1>
                </span>
              </div>
            </div>
          </div>
      </div>
    </div>
    );
}
