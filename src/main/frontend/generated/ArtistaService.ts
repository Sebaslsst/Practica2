import { EndpointRequestInit as EndpointRequestInit_1 } from "@vaadin/hilla-frontend";
import type Pageable_1 from "./com/vaadin/hilla/mappedtypes/Pageable.js";
import client_1 from "./connect-client.default.js";
import type Artista_1 from "./practica/com/base/models/Artista.js";
async function createArtista_1(nombre: string | undefined, nacionalidad: string | undefined, init?: EndpointRequestInit_1): Promise<void> { return client_1.call("ArtistaService", "createArtista", { nombre, nacionalidad }, init); }
async function list_1(pageable: Pageable_1 | undefined, init?: EndpointRequestInit_1): Promise<Array<Artista_1 | undefined> | undefined> { return client_1.call("ArtistaService", "list", { pageable }, init); }
async function listAll_1(init?: EndpointRequestInit_1): Promise<Array<Artista_1 | undefined> | undefined> { return client_1.call("ArtistaService", "listAll", {}, init); }
async function listCountry_1(init?: EndpointRequestInit_1): Promise<Array<string | undefined> | undefined> { return client_1.call("ArtistaService", "listCountry", {}, init); }
async function listRolArtista_1(init?: EndpointRequestInit_1): Promise<Array<string | undefined> | undefined> { return client_1.call("ArtistaService", "listRolArtista", {}, init); }
async function updateArtista_1(id: number | undefined, nombre: string | undefined, nacionalidad: string | undefined, init?: EndpointRequestInit_1): Promise<void> { return client_1.call("ArtistaService", "updateArtista", { id, nombre, nacionalidad }, init); }
export { createArtista_1 as createArtista, list_1 as list, listAll_1 as listAll, listCountry_1 as listCountry, listRolArtista_1 as listRolArtista, updateArtista_1 as updateArtista };
