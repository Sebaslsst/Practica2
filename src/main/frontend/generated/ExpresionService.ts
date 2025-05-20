import { EndpointRequestInit as EndpointRequestInit_1 } from "@vaadin/hilla-frontend";
import client_1 from "./connect-client.default.js";
import type Expresion_1 from "./practica/com/base/models/Expresion.js";
async function create_1(expresion: string | undefined, init?: EndpointRequestInit_1): Promise<void> { return client_1.call("ExpresionService", "create", { expresion }, init); }
async function listAll_1(init?: EndpointRequestInit_1): Promise<Array<Expresion_1 | undefined> | undefined> { return client_1.call("ExpresionService", "listAll", {}, init); }
export { create_1 as create, listAll_1 as listAll };
