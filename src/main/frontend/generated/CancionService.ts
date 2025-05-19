import { EndpointRequestInit as EndpointRequestInit_1 } from "@vaadin/hilla-frontend";
import type Pageable_1 from "./com/vaadin/hilla/mappedtypes/Pageable.js";
import client_1 from "./connect-client.default.js";
import type Cancion_1 from "./practica/com/base/models/Cancion.js";
import type TipoArchivoEnum_1 from "./practica/com/base/models/TipoArchivoEnum.js";
async function createCancion_1(nombre: string | undefined, id_genero: number | undefined, duracion: number | undefined, url: string | undefined, tipo: TipoArchivoEnum_1 | undefined, id_album: number | undefined, init?: EndpointRequestInit_1): Promise<void> { return client_1.call("CancionService", "createCancion", { nombre, id_genero, duracion, url, tipo, id_album }, init); }
async function list_1(pageable: Pageable_1 | undefined, init?: EndpointRequestInit_1): Promise<Array<Cancion_1 | undefined> | undefined> { return client_1.call("CancionService", "list", { pageable }, init); }
async function listAll_1(init?: EndpointRequestInit_1): Promise<Array<Cancion_1 | undefined> | undefined> { return client_1.call("CancionService", "listAll", {}, init); }
async function listTiposArchivo_1(init?: EndpointRequestInit_1): Promise<Array<string | undefined> | undefined> { return client_1.call("CancionService", "listTiposArchivo", {}, init); }
async function updateCancion_1(id: number | undefined, nombre: string | undefined, id_genero: number | undefined, duracion: number | undefined, url: string | undefined, tipo: TipoArchivoEnum_1 | undefined, id_album: number | undefined, init?: EndpointRequestInit_1): Promise<void> { return client_1.call("CancionService", "updateCancion", { id, nombre, id_genero, duracion, url, tipo, id_album }, init); }
export { createCancion_1 as createCancion, list_1 as list, listAll_1 as listAll, listTiposArchivo_1 as listTiposArchivo, updateCancion_1 as updateCancion };
