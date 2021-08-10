import React, {Component} from 'react';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';

export default class ApproveRejectApplicationDialog extends Component {


    render() {
        return (
            <Dialog open={this.props.open} onClose={this.props.onClose} aria-labelledby="form-dialog-title">
                <DialogTitle id="form-dialog-title"> <font size="6">Mentorluk Başvurusu Onaylama -
                    Reddetme </font></DialogTitle>
                <DialogContent>

                    <font size="5"> Konu adı: <b>{this.props.subject_name}</b></font>
                    <br/>
                    <font size="5">Altkonu adı:<b> {this.props.subsubject_name}</b></font>
                    <br/>
                    <font size="5"> Kullanıcı adı: <b>{this.props.applicant_username}</b></font>
                </DialogContent>
                <DialogActions>
                    <Button onClick={this.props.onClose} color="default">
                        İptal
                    </Button>
                    <Button
                        onClick={() => this.props.handleApplicationApprovalRejection(this.props.applicant_username, this.props.subject_name, this.props.subsubject_name, "reject")}
                        color="secondary">
                        Reddet
                    </Button>
                    <Button
                        statuscolor="primary">
                        Onayla
                    </Button>
                </DialogActions>
            </Dialog>
        );
    }
}