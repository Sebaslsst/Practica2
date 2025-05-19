import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { Button, Dialog, Grid, GridColumn, TextField, VerticalLayout } from '@vaadin/react-components';
import { Notification } from '@vaadin/react-components/Notification';

import { useSignal } from '@vaadin/hilla-react-signals';
import handleError from 'Frontend/views/_ErrorHandler';
import { Group, ViewToolbar } from 'Frontend/components/ViewToolbar';
import { useDataProvider } from '@vaadin/hilla-react-crud';
import { ArtistaBandaService } from 'Frontend/generated/endpoints';

export const config: ViewConfig = {
  title: 'Artista Banda',
  menu: {
    icon: 'vaadin:clipboard-check',
    order: 3,
    title: 'Artista Banda',
  },
};

type BandaEntryFormProps = {
  onBandaCreated?: () => void;
};

function BandaEntryForm(props: BandaEntryFormProps) {
  const dialogOpened = useSignal(false);
  const nombre = useSignal('');

  const open = () => {
    dialogOpened.value = true;
  };

  const close = () => {
    dialogOpened.value = false;
    nombre.value = '';
  };

  const createBanda = async () => {
    try {
      if (nombre.value.trim().length > 0) {
        // Use the correct method from ArtistaBandaService
        await ArtistaBandaService.createBanda(nombre.value, undefined);
        if (props.onBandaCreated) {
          props.onBandaCreated();
        }
        nombre.value = '';
        dialogOpened.value = false;
        Notification.show('Banda creada exitosamente', { duration: 5000, position: 'bottom-end', theme: 'success' });
      } else {
        Notification.show('No se pudo crear, faltan datos', { duration: 5000, position: 'top-center', theme: 'error' });
      }
    } catch (error) {
      console.log(error);
      handleError(error);
    }
  };

  return (
    <>
      <Button onClick={open} theme="primary">Registrar Banda</Button>
      <Dialog
        aria-label="Registrar Banda"
        draggable
        modeless
        opened={dialogOpened.value}
        onOpenedChanged={(event) => {
          dialogOpened.value = event.detail.value;
        }}
        header={
          <h2
            className="draggable"
            style={{
              flex: 1,
              cursor: 'move',
              margin: 0,
              fontSize: '1.5em',
              fontWeight: 'bold',
              padding: 'var(--lumo-space-m) 0',
            }}
          >
            Registrar Banda
          </h2>
        }
        footerRenderer={() => (
          <>
            <Button onClick={close}>Cancelar</Button>
            <Button theme="primary" onClick={createBanda}>
              Registrar
            </Button>
          </>
        )}
      >
        <VerticalLayout
          theme="spacing"
          style={{ width: '300px', maxWidth: '100%', alignItems: 'stretch' }}
        >
          <TextField
            label="Nombre"
            placeholder="Ingrese el nombre de la banda"
            aria-label="Ingrese el nombre de la banda"
            value={nombre.value}
            onValueChanged={e => (nombre.value = e.detail.value)}
          />
        </VerticalLayout>
      </Dialog>
    </>
  );
}

function LinkRenderer() {
  return (
    <span>
      <Button>
        Editar
      </Button>
    </span>
  );
}

function IndexRenderer({ model }: { model: { index: number } }) {
  return <span>{model.index + 1}</span>;
}

export default function ArtistaBandaListView() {
  const dataProvider = useDataProvider({
    list: async (request, filter) => {
      return await ArtistaBandaService.listAll();
    },
  });

  return (
    <main className="w-full h-full flex flex-col box-border gap-s p-m">
      <ViewToolbar title="Bandas">
        <Group>
          <BandaEntryForm onBandaCreated={dataProvider.refresh} />
        </Group>
      </ViewToolbar>
      <Grid dataProvider={dataProvider.dataProvider}>
        <GridColumn header="Nro" renderer={IndexRenderer} />
        <GridColumn path="artista" header="Artista" />
        <GridColumn path="banda" header="Banda" />
        <GridColumn path="rol" header="Rol" />
        <GridColumn header="Acciones" renderer={LinkRenderer} />
      </Grid>
    </main>
  );
}
