
import Home from "./pages/Home.svelte";
import Helper from "./pages/Helper.svelte";
import Needy from "./pages/Needy.svelte";
import Search from "./pages/Search.svelte";
import HelperId from "./pages/HelperId.svelte"


export default {
  '/': Home,
  '/home': Home,
  '/helpers': Helper,
  '/needies': Needy,
  '/search': Search,
  '/helper/:id': HelperId,
  

  
}

