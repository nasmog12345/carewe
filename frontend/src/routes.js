import NeedyHome from "./pages/NeedyHome.svelte";
import Helper from "./pages/Helper.svelte";
import Search from "./pages/Search.svelte";
import HelperId from "./pages/HelperDetails.svelte";
import Account from "./pages/Account.svelte";
import NeedyDetails from "./pages/NeedyDetails.svelte";
import CreateNeedy from "./pages/CreateNeedy.svelte";
import CreateHelper from "./pages/CreateHelper.svelte";
import Start from "./pages/Start.svelte";
import HelperHome from "./pages/HelperHome.svelte";
import AllNeedy from "./pages/AllNeedy.svelte";
import AllHelper from "./pages/AllHelper.svelte";
import HelperDetails from "./pages/HelperDetails.svelte";
import WeatherAPI from "./pages/WeatherAPI.svelte";

export default {
  "/": Start,
  //'/': Home,
  // '/home': Home,
  "/helpers": Helper,
  "/needyhome": NeedyHome,
  "/helperhome": HelperHome,
  "/needy": CreateNeedy,
  "/helper": CreateHelper,
  "/search": Search,
  "/helper/:id": HelperDetails,
  "/needy/:id": NeedyDetails,
  "/account": Account,
  "/allneedy": AllNeedy,
  "/allhelper": AllHelper,
  "/weather": WeatherAPI,
};
