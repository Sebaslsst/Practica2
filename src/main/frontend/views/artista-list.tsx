import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { Button, ComboBox, Dialog, Grid, GridColumn, GridItemModel, TextField, VerticalLayout } from '@vaadin/react-components';
import { Notification } from '@vaadin/react-components/Notification';
import { ArtistaService } from 'Frontend/generated/endpoints';
import { useSignal } from '@vaadin/hilla-react-signals';
import handleError from 'Frontend/views/_ErrorHandler';
import { Group, ViewToolbar } from 'Frontend/components/ViewToolbar';
import { useDataProvider } from '@vaadin/hilla-react-crud';
import Artista from 'Frontend/generated/practica/com/base/models/Artista';
import { useEffect } from 'react';

export const config: ViewConfig = {
  title: 'Artistas',
  menu: {
    icon: 'vaadin:clipboard-check',
    order: 1,
    title: 'Artistas',
  },
};

type ArtistaEntryFormProps = {
  onArtistaCreated?: () => void;
};

type ArtistaEntryFormUpdateProps = {
  artista: Artista;
  onArtistaUpdated?: () => void;
};

// Formulario para crear artista
const ArtistaEntryForm: React.FC<ArtistaEntryFormProps> = (props) => {
  const nombre = useSignal('');
  const nacionalidad = useSignal('');
  const dialogOpened = useSignal(false);
  const pais = useSignal<string[]>([]);

  useEffect(() => {
    ArtistaService.listCountry().then(data => {
      pais.value = (data ?? []).filter((p): p is string => typeof p === 'string');
    });
  }, []);

  const createArtista = async () => {
    try {
      if (nombre.value.trim().length > 0 && nacionalidad.value.trim().length > 0) {
        await ArtistaService.createArtista(nombre.value, nacionalidad.value);
        if (props.onArtistaCreated) {
          props.onArtistaCreated();
        }
        nombre.value = '';
        nacionalidad.value = '';
        dialogOpened.value = false;
        Notification.show('Artista creado', { duration: 5000, position: 'bottom-end', theme: 'success' });
      } else {
        Notification.show('No se pudo crear, faltan datos', { duration: 5000, position: 'top-center', theme: 'error' });
      }
    } catch (error) {
      handleError(error);
    }
  };

  return (
    <>
      <Dialog
        modeless
        headerTitle="Nuevo artista"
        opened={dialogOpened.value}
        onOpenedChanged={({ detail }) => {
          dialogOpened.value = detail.value;
        }}
        footer={
          <>
            <Button onClick={() => (dialogOpened.value = false)}>Cancelar</Button>
            <Button onClick={createArtista} theme="primary">Registrar</Button>
          </>
        }
      >
        <VerticalLayout style={{ alignItems: 'stretch', width: '18rem', maxWidth: '100%' }}>
          <TextField
            label="Nombre del artista"
            placeholder="Ingrese el nombre del artista"
            aria-label="Nombre del artista"
            value={nombre.value}
            onValueChanged={(evt) => (nombre.value = evt.detail.value)}
          />
          <ComboBox
            label="Nacionalidad"
            items={pais.value}
            placeholder="Seleccione un país"
            aria-label="Seleccione un país de la lista"
            value={nacionalidad.value}
            onValueChanged={(evt) => (nacionalidad.value = evt.detail.value)}
          />
        </VerticalLayout>
      </Dialog>
      <Button onClick={() => (dialogOpened.value = true)}>Agregar</Button>
    </>
  );
};

// Formulario para actualizar artista
const ArtistaEntryFormUpdate: React.FC<ArtistaEntryFormUpdateProps> = (props) => {
  const pais = useSignal<string[]>([]);
  useEffect(() => {
    ArtistaService.listCountry().then(data => {
      pais.value = (data ?? []).filter((p): p is string => typeof p === 'string');
    });
  }, []);
  const nombre = useSignal(props.artista.nombres ?? '');
  const nacionalidad = useSignal(props.artista.nacionalidad ?? '');
  const dialogOpened = useSignal(false);

  const updateArtista = async () => {
    try {
      if (nombre.value.trim().length > 0 && nacionalidad.value.trim().length > 0) {
        await ArtistaService.updateArtista(props.artista.id, nombre.value, nacionalidad.value);
        if (props.onArtistaUpdated) {
          props.onArtistaUpdated();
        }
        dialogOpened.value = false;
        Notification.show('Artista actualizado', { duration: 5000, position: 'bottom-end', theme: 'success' });
      } else {
        Notification.show('No se pudo actualizar, faltan datos', { duration: 5000, position: 'top-center', theme: 'error' });
      }
    } catch (error) {
      handleError(error);
    }
  };

  return (
    <>
      <Dialog
        modeless
        headerTitle="Actualizar artista"
        opened={dialogOpened.value}
        onOpenedChanged={({ detail }) => {
          dialogOpened.value = detail.value;
        }}
        footer={
          <>
            <Button onClick={() => (dialogOpened.value = false)}>Cancelar</Button>
            <Button onClick={updateArtista} theme="primary">Actualizar</Button>
          </>
        }
      >
        <VerticalLayout style={{ alignItems: 'stretch', width: '18rem', maxWidth: '100%' }}>
          <TextField
            label="Nombre del artista"
            placeholder="Ingrese el nombre del artista"
            aria-label="Nombre del artista"
            value={nombre.value}
            onValueChanged={(evt) => (nombre.value = evt.detail.value)}
          />
          <ComboBox
            label="Nacionalidad"
            items={pais.value}
            placeholder="Seleccione un país"
            aria-label="Seleccione un país de la lista"
            value={nacionalidad.value}
            onValueChanged={(evt) => (nacionalidad.value = evt.detail.value)}
          />
        </VerticalLayout>
      </Dialog>
      <Button onClick={() => (dialogOpened.value = true)}>Editar</Button>
    </>
  );
};

export default function ArtistaView() {
  const dataProvider = useDataProvider<Artista>({
    list: async () => {
      const result = await ArtistaService.listAll();
      return (result ?? []).filter((a): a is Artista => !!a);
    },
  });

  // Renderer para la columna de índice
  const indexRenderer = ({ model }: { model: GridItemModel<Artista> }) => (
    <span>{model.index + 1}</span>
  );

  const accionesRenderer = ({ item }: { item: Artista }) => (
    <span>
      <ArtistaEntryFormUpdate
        artista={item}
        onArtistaUpdated={() => {
          dataProvider.refresh();
        }}
      />
    </span>
  );

  return (
    <main className="w-full h-full flex flex-col box-border gap-s p-m">
      <ViewToolbar title="Lista de artista">
        <Group>
          <ArtistaEntryForm onArtistaCreated={dataProvider.refresh} />
        </Group>
      </ViewToolbar>
      <Grid dataProvider={dataProvider.dataProvider}>
        <GridColumn renderer={indexRenderer} header="Nro" />
        <GridColumn path="nombres" header="Nombre del artista" />
        <GridColumn path="nacionalidad" header="Nacionalidad" />
        <GridColumn header="Acciones" renderer={accionesRenderer} />
      </Grid>
    </main>
  );
}