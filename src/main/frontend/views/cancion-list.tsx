import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { Button, ComboBox, Dialog, Grid, GridColumn, GridItemModel, TextField, VerticalLayout, GridSortColumn, HorizontalLayout, Select, Icon } from '@vaadin/react-components';
import { Notification } from '@vaadin/react-components/Notification';
import { CancionService } from 'Frontend/generated/endpoints';
import { useSignal } from '@vaadin/hilla-react-signals';
import handleError from 'Frontend/views/_ErrorHandler';
import { Group, ViewToolbar } from 'Frontend/components/ViewToolbar';
import { useEffect, useState } from 'react';
import TipoArchivoEnum from 'Frontend/generated/practica/com/base/models/TipoArchivoEnum';
import Cancion from 'Frontend/generated/practica/com/base/models/Cancion';

export const config: ViewConfig = {
  title: 'Cancion',
  menu: {
    icon: 'vaadin:music',
    order: 1,
    title: 'Cancion',
  },
};

type CancionEntryFormProps = {
  onCancionCreated?: () => void;
};

type CancionEntryFormUpdateProps = {
  item: Cancion;
  onCancionUpdated?: () => void;
};

// FORMULARIO CREAR CANCIÓN
function CancionEntryForm(props: CancionEntryFormProps) {
  const nombre = useSignal('');
  const genero = useSignal('');
  const duracion = useSignal('');
  const url = useSignal('');
  const tipo = useSignal('');
  const album = useSignal('');
  const dialogOpened = useSignal(false);

  // Listas para ComboBox
  const generos = useSignal<{ value: string, label: string }[]>([]);
  const albums = useSignal<{ value: string, label: string }[]>([]);
  const tipos = useSignal<string[]>([]);

  useEffect(() => {
    CancionService.listaAlbumGenero().then(data => {
      generos.value = (data ?? []).map((g: any) => ({
        value: g.value,
        label: g.label
      }));
    });
    CancionService.listaAlbumCombo().then(data => {
      albums.value = (data ?? []).map((v: any) => ({
        value: v.value,
        label: v.label
      }));
    });
    CancionService.listTiposArchivo().then(data => {
      tipos.value = (data ?? []).filter((e): e is string => typeof e === 'string');
    });
  }, []);

  const createCancion = async () => {
    try {
      if (
        nombre.value.trim() &&
        genero.value &&
        duracion.value.trim() &&
        url.value.trim() &&
        tipo.value &&
        album.value
      ) {
        const id_genero = parseInt(genero.value);
        const id_album = parseInt(album.value);
        const tipoEnum = TipoArchivoEnum[tipo.value as keyof typeof TipoArchivoEnum];

        await CancionService.createCancion(
          nombre.value,
          id_genero,
          parseInt(duracion.value),
          url.value,
          tipoEnum,
          id_album
        );
        if (props.onCancionCreated) props.onCancionCreated();
        nombre.value = '';
        genero.value = '';
        duracion.value = '';
        url.value = '';
        tipo.value = '';
        album.value = '';
        dialogOpened.value = false;
        Notification.show('Publicación creada', { duration: 5000, position: 'bottom-end', theme: 'success' });
      } else {
        Notification.show('No se pudo crear, faltan o hay datos inválidos', { duration: 5000, position: 'top-center', theme: 'error' });
      }
    } catch (error) {
      handleError(error);
    }
  };

  return (
    <>
      <Dialog
        modeless
        headerTitle="Nueva publicación"
        opened={dialogOpened.value}
        onOpenedChanged={({ detail }: { detail: { value: boolean } }) => {
          dialogOpened.value = detail.value;
        }}
        footer={
          <>
            <Button onClick={() => (dialogOpened.value = false)}>Cancelar</Button>
            <Button onClick={createCancion} theme="primary">Registrar</Button>
          </>
        }
      >
        <VerticalLayout style={{ alignItems: 'stretch', width: '18rem', maxWidth: '100%' }}>
          <TextField
            label="Título"
            value={nombre.value}
            onValueChanged={(evt: CustomEvent<{ value: string }>) => (nombre.value = evt.detail.value)}
          />
          <ComboBox
            label="Género"
            items={generos.value}
            itemLabelPath="label"
            itemValuePath="value"
            value={genero.value}
            onValueChanged={(evt: CustomEvent<{ value: string }>) => (genero.value = evt.detail.value)}
          />
          <ComboBox
            label="Tipo"
            items={tipos.value}
            itemLabelPath="label"
            itemValuePath="value"
            value={tipo.value}
            onValueChanged={(evt: CustomEvent<{ value: string }>) => (tipo.value = evt.detail.value)}
          />
          <ComboBox
            label="Album"
            items={albums.value}
            itemLabelPath="label"
            itemValuePath="value"
            value={album.value}
            onValueChanged={(evt: CustomEvent<{ value: string }>) => (album.value = evt.detail.value)}
          />
          <TextField
            label="Duración"
            value={duracion.value}
            onValueChanged={(evt: CustomEvent<{ value: string }>) => (duracion.value = evt.detail.value)}
            placeholder="Ingrese la duración en segundos"
          />
          <TextField
            label="URL"
            value={url.value}
            onValueChanged={(evt: CustomEvent<{ value: string }>) => (url.value = evt.detail.value)}
            placeholder="Ingrese la URL"
          />
        </VerticalLayout>
      </Dialog>
      <Button onClick={() => (dialogOpened.value = true)}>Agregar</Button>
    </>
  );
}

// FORMULARIO EDITAR CANCIÓN
function CancionEntryFormUpdate(props: CancionEntryFormUpdateProps) {
  const dialogOpened = useSignal(false);

  const nombre = useSignal(props.item.nombre ?? '');
  const genero = useSignal(String(props.item.id_genero ?? ''));
  const duracion = useSignal(String(props.item.duracion ?? ''));
  const url = useSignal(props.item.url ?? '');
  const tipo = useSignal(String(props.item.tipo ?? ''));
  const album = useSignal(String(props.item.id_album ?? ''));

  const generos = useSignal<{ value: string, label: string }[]>([]);
  const albums = useSignal<{ value: string, label: string }[]>([]);
  const tipos = useSignal<string[]>([]);

  useEffect(() => {
    CancionService.listaAlbumGenero().then(data => {
      generos.value = (data ?? []).map((g: any) => ({
        value: g.value,
        label: g.label
      }));
    });
    CancionService.listaAlbumCombo().then(data => {
      albums.value = (data ?? []).map((v: any) => ({
        value: v.value,
        label: v.label
      }));
    });
    CancionService.listTiposArchivo().then(data => {
      tipos.value = (data ?? []).filter((e): e is string => typeof e === 'string');
    });
  }, []);

  const updateCancion = async () => {
    try {
      if (
        nombre.value.trim() &&
        genero.value &&
        duracion.value.trim() &&
        url.value.trim() &&
        tipo.value &&
        album.value
      ) {
        const id_genero = parseInt(genero.value);
        const id_album = parseInt(album.value);
        const tipoEnum = TipoArchivoEnum[tipo.value as keyof typeof TipoArchivoEnum];

        await CancionService.updateCancion(
          props.item.id,
          nombre.value,
          id_genero,
          parseInt(duracion.value),
          url.value,
          tipoEnum,
          id_album
        );
        if (props.onCancionUpdated) props.onCancionUpdated(); 
        dialogOpened.value = false;
        Notification.show('Canción actualizada', { duration: 5000, position: 'bottom-end', theme: 'success' });
      }
    } catch (error) {
      handleError(error);
    }
  };

  return (
    <>
      <Dialog
        aria-label="Editar Canción"
        draggable
        modeless
        opened={dialogOpened.value}
        onOpenedChanged={(event) => {
          dialogOpened.value = event.detail.value;
        }}
        headerTitle="Editar Canción"
        footer={
          <>
            <Button onClick={() => (dialogOpened.value = false)}>Cancelar</Button>
            <Button theme="primary" onClick={updateCancion}>Actualizar</Button>
          </>
        }
      >
        <VerticalLayout style={{ alignItems: 'stretch', width: '18rem', maxWidth: '100%' }}>
          <TextField
            label="Título"
            value={nombre.value}
            onValueChanged={(evt: CustomEvent<{ value: string }>) => (nombre.value = evt.detail.value)}
          />
          <ComboBox
            label="Género"
            items={generos.value}
            itemLabelPath="label"
            itemValuePath="value"
            value={genero.value}
            onValueChanged={(evt: CustomEvent<{ value: string }>) => (genero.value = evt.detail.value)}
          />
          <ComboBox
            label="Tipo"
            items={tipos.value}
            itemLabelPath="label"
            itemValuePath="value"
            value={tipo.value}
            onValueChanged={(evt: CustomEvent<{ value: string }>) => (tipo.value = evt.detail.value)}
          />
          <ComboBox
            label="Album"
            items={albums.value}
            itemLabelPath="label"
            itemValuePath="value"
            value={album.value}
            onValueChanged={(evt: CustomEvent<{ value: string }>) => (album.value = evt.detail.value)}
          />
          <TextField
            label="Duración"
            value={duracion.value}
            onValueChanged={(evt: CustomEvent<{ value: string }>) => (duracion.value = evt.detail.value)}
            placeholder="Ingrese la duración en segundos"
          />
          <TextField
            label="URL"
            value={url.value}
            onValueChanged={(evt: CustomEvent<{ value: string }>) => (url.value = evt.detail.value)}
            placeholder="Ingrese la URL"
          />
        </VerticalLayout>
      </Dialog>
      <Button onClick={() => (dialogOpened.value = true)}>Editar</Button>
    </>
  );
}

  // LISTA DE Canciones
  export default function CancionView() {
    const [items, setItems] = useState<any[]>([]);
    const callData = () => {
      CancionService.listCancion().then(function (data) {
        setItems((data ?? []).filter((item: any) => item !== undefined));
      });
    };

    useEffect(() => {
      callData();
    }, []);


    function indexIndex({ model }: { model: GridItemModel<any> }) {
      return <span>{model.index + 1}</span>;
    }

    function acciones({ item }: { item: Cancion }) {
      return <CancionEntryFormUpdate item={item} onCancionUpdated={callData} />;
    }
    // Para buscar 
    const criterio = useSignal('');
    const texto = useSignal('');
    const itemSelect = [
      {
        label: 'Título',
        value: 'nombre',
      },
      {
        label: 'Albúm',
        value: 'album',
      },
      {
        label: 'Género',
        value: 'genero',
      },
      {
        label: 'Tipo',
        value: 'tipo',
      },
    ];
    const [searchActive, setSearchActive] = useState(false);
    const [searchResults, setSearchResults] = useState<any[]>([]);

    // Search function
    const search = async () => {
      try {
        if (!criterio.value) {
          Notification.show('Debe seleccionar un criterio de búsqueda.', {
            duration: 5000,
            position: 'top-center',
            theme: 'error',
          });
          return;
        }

        CancionService.search(criterio.value, texto.value, 0).then(function (data) {
          const filtered = (data ?? []).filter((item: any) => item !== undefined);
          setItems(filtered);
          setSearchResults(filtered);
          setSearchActive(true);
        });

        criterio.value = '';
        texto.value = '';

        Notification.show('Busqueda realizada', { duration: 5000, position: 'bottom-end', theme: 'success' });

      } catch (error) {
        console.log(error);
        handleError(error);
      }
    };

    const handleOrder = async (event: any, columnId: string) => {
      const direction = event.detail.value;
      const asc = direction === 'asc';
      try {
        if (searchActive && searchResults.length > 0) {
          // Ordenar en el frontend los resultados de búsqueda
          const sorted = [...searchResults].sort((a, b) => {
            if (a[columnId] < b[columnId]) return asc ? -1 : 1;
            if (a[columnId] > b[columnId]) return asc ? 1 : -1;
            return 0;
          });
          setItems(sorted);
        } else {
 
          const data = await CancionService.listOrdenadoPor(columnId, asc);
          setItems((data ?? []).filter((item: any) => item !== undefined));
        }
      } catch (error) {
        Notification.show('Error al ordenar', { duration: 5000, position: 'top-center', theme: 'error' });
      }
    };

    return (
      <main className="w-full h-full flex flex-col box-border gap-s p-m">
        <ViewToolbar title="Lista de Canciones">
          <Group>
            <CancionEntryForm onCancionCreated={callData} />
          </Group>
        </ViewToolbar>
        <HorizontalLayout theme="spacing">
          <Select
            items={itemSelect}
            value={criterio.value}
            onValueChanged={(evt) => (criterio.value = evt.detail.value)}
            placeholder="Seleccione un criterio"
          />
          <TextField
            placeholder="Buscar"
            style={{ width: '50%' }}
            value={texto.value}
            onValueChanged={(evt) => (texto.value = evt.detail.value)}
          >
            <Icon slot="prefix" icon="vaadin:search" />
          </TextField>
          <Button onClick={search} theme="primary">
            Buscar
          </Button>
        </HorizontalLayout>
        <Grid items={items}>
          <GridColumn renderer={indexIndex} header="ID" />
          <GridSortColumn path="nombre" header="Título" onDirectionChanged={(e) => handleOrder(e, 'nombre')} />
          <GridSortColumn path="album" header="Álbum" onDirectionChanged={(e) => handleOrder(e, 'album')} />
          <GridSortColumn path="genero" header="Género" onDirectionChanged={(e) => handleOrder(e, 'genero')} />
          <GridSortColumn path="duracion" header="Duración" onDirectionChanged={(e) => handleOrder(e, 'duracion')} />
          <GridColumn path="url" header="URL" />
          <GridSortColumn path="tipo" header="Tipo" onDirectionChanged={(e) => handleOrder(e, 'tipo')} />
          <GridColumn header="Acciones" renderer={acciones} />
        </Grid>
      </main>
    );
  }