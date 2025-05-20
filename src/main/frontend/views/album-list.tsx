import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { Button, Dialog, Grid, GridColumn, TextField, DatePicker, VerticalLayout } from '@vaadin/react-components';
import { Notification } from '@vaadin/react-components/Notification';
import { useSignal } from '@vaadin/hilla-react-signals';
import handleError from 'Frontend/views/_ErrorHandler';
import { Group, ViewToolbar } from 'Frontend/components/ViewToolbar';
import { useDataProvider } from '@vaadin/hilla-react-crud';
import { AlbumService } from 'Frontend/generated/endpoints';

type Album = {
  id?: number;
  nombre?: string;
  fecha?: string;
  id_banda?: number;
};

export const config: ViewConfig = {
  title: 'Álbumes',
  menu: {
    icon: 'vaadin:clipboard-check',
    order: 2,
    title: 'Álbumes',
  },
};

type AlbumEntryFormProps = {
  onAlbumCreated?: () => void;
};

const AlbumEntryForm: React.FC<AlbumEntryFormProps> = (props) => {
  const nombre = useSignal('');
  const fecha = useSignal('');
  const idBanda = useSignal('');
  const dialogOpened = useSignal(false);

  const createAlbum = async () => {
    try {
      if (nombre.value.trim().length > 0 && fecha.value && idBanda.value.trim().length > 0) {
        await AlbumService.createAlbum(nombre.value, fecha.value, parseInt(idBanda.value));
        if (props.onAlbumCreated) {
          props.onAlbumCreated();
        }
        nombre.value = '';
        fecha.value = '';
        idBanda.value = '';
        dialogOpened.value = false;
        Notification.show('Álbum creado', { duration: 5000, position: 'bottom-end', theme: 'success' });
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
        headerTitle="Nuevo álbum"
        opened={dialogOpened.value}
        onOpenedChanged={({ detail }) => {
          dialogOpened.value = detail.value;
        }}
        footer={
          <>
            <Button onClick={() => (dialogOpened.value = false)}>Cancelar</Button>
            <Button onClick={createAlbum} theme="primary">Registrar</Button>
          </>
        }
      >
        <VerticalLayout style={{ alignItems: 'stretch', width: '18rem', maxWidth: '100%' }}>
          <TextField
            label="Nombre del álbum"
            value={nombre.value}
            onValueChanged={e => (nombre.value = e.detail.value)}
          />
          <DatePicker
            label="Fecha"
            value={fecha.value}
            onValueChanged={e => (fecha.value = e.detail.value)}
          />
          <TextField
            label="ID Banda"
            value={idBanda.value}
            onValueChanged={e => (idBanda.value = e.detail.value)}
          />
        </VerticalLayout>
      </Dialog>
      <Button onClick={() => (dialogOpened.value = true)}>Agregar álbum</Button>
    </>
  );
};

export default function AlbumView() {
  const dataProvider = useDataProvider<Album>({
    list: async () => {
      const result = await AlbumService.listAllAlbum();
      return (result ?? []).filter((a): a is Album => !!a);
    },
  });

  const indexRenderer = ({ model }: { model: any }) => (
    <span>{model.index + 1}</span>
  );

  return (
    <main className="w-full h-full flex flex-col box-border gap-s p-m">
      <ViewToolbar title="Lista de álbumes">
        <Group>
          <AlbumEntryForm onAlbumCreated={dataProvider.refresh} />
        </Group>
      </ViewToolbar>
      <Grid dataProvider={dataProvider.dataProvider}>
        <GridColumn renderer={indexRenderer} header="Nro" />
        <GridColumn path="nombre" header="Nombre del álbum" />
        <GridColumn path="fecha" header="Fecha" />
      </Grid>
    </main>
  );
}