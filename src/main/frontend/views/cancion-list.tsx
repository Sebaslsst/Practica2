import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { Button, ComboBox, Dialog, Grid, GridColumn, GridItemModel, DatePicker, TextField, VerticalLayout } from '@vaadin/react-components';
import { Notification } from '@vaadin/react-components/Notification';
import { CancionService } from 'Frontend/generated/endpoints';
import { useSignal } from '@vaadin/hilla-react-signals';
import handleError from 'Frontend/views/_ErrorHandler';
import { Group, ViewToolbar } from 'Frontend/components/ViewToolbar';
import { useDataProvider } from '@vaadin/hilla-react-crud';
import { useEffect } from 'react';
import TipoArchivoEnum from 'Frontend/generated/practica/com/base/models/TipoArchivoEnum';

export const config: ViewConfig = {
  title: 'Cancion',
  menu: {
    icon: 'vaadin:book',
    order: 1,
    title: 'Cancion',
  },
};

type CancionEntryFormProps = {
  onCancionCreated?: () => void;
};

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
        // Conversión de valores
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

// LISTA DE Canciones
export default function CancionView() {
  const dataProvider = useDataProvider<any>({
    list: async () => {
      const result = await CancionService.listCancion();
      return (result ?? []).filter((item): item is Record<string, unknown> => item !== undefined);
    },
  });

  function indexIndex({ model }: { model: GridItemModel<any> }) {
    return <span>{model.index + 1}</span>;
  }

  return (
    <main className="w-full h-full flex flex-col box-border gap-s p-m">
      <ViewToolbar title="Lista de Canciones">
        <Group>
          <CancionEntryForm onCancionCreated={dataProvider.refresh} />
        </Group>
      </ViewToolbar>
      <Grid dataProvider={dataProvider.dataProvider}>
        <GridColumn renderer={indexIndex} header="ID" />
        <GridColumn path="nombre" header="Título" />
        <GridColumn path="genero" header="Género" />
        <GridColumn path="duracion" header="Duración" />
        <GridColumn path="url" header="URL" />
        <GridColumn path="tipo" header="Tipo" />
      </Grid>
    </main>
  );
}