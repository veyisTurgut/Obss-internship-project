import React, {Component} from 'react';
import Button from '@material-ui/core/Button';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogTitle from '@material-ui/core/DialogTitle';

export default class ApproveRejectApplicationDialog extends Component {


    render() {
        return (
            <Dialog open={this.props.open} onClose={this.props.onClose} aria-labelledby="form-dialog-title">
                <DialogTitle id="form-dialog-title"> <font size="6">Mentorluk Başvurusu Onaylama -
                    Reddetme </font></DialogTitle>
                <DialogContent>

                    <font size="5"> <i><u>Konu adı: </u></i> <b>{this.props.subject.subject_name}</b></font>
                    <br/>
                    <font size="5"> <i><u>Altkonu adı:</u></i><b> {this.props.subject.subsubject_name}</b></font>
                    <br/>
                    <font size="5"> <i><u>Kullanıcı adı:</u></i> <b>{this.props.subject.applicant_username}</b></font>
                    <br/>
                    <font size="5"> <i><u>Tecrübeleri: </u></i>{this.props.subject.experience}</font>
                </DialogContent>
                <DialogActions>
                    <Button onClick={this.props.onClose} color="default">
                        İptal
                    </Button>
                    <Button
                        onClick={() => this.props.handleApplicationApprovalRejection(this.props.subject.applicant_username,
                            this.props.subject.subject_name, this.props.subject.subsubject_name, "reject")}
                        color="secondary">
                        Reddet
                    </Button>
                    <Button
                        onClick={() => this.props.handleApplicationApprovalRejection(this.props.subject.applicant_username,
                            this.props.subject.subject_name, this.props.subject.subsubject_name, "approve")}
                        statuscolor="primary">
                        Onayla
                    </Button>
                </DialogActions>
            </Dialog>
        );
    }
}