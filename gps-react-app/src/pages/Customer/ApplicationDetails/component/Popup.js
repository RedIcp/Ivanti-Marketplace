import React from 'react'
import { Dialog, DialogContent} from '@material-ui/core';

export default function Popup(props) {
    const {children, openPopup, setOpenPopup } = props;

    return (
        <Dialog open={openPopup}>
            <DialogContent dividers>
                {children}
            </DialogContent>
        </Dialog>
    )
}