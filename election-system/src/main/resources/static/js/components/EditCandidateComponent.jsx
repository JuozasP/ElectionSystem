var styles = {
    line: {
        borderBottom: '1px solid #888',
        margin: '20 0 20 0'
    },
    blue: {
        color: '#0080ff'
    }
};

var EditCandidateComponent = React.createClass({
    render: function () {
        return (
            <form>
                <h2 style={styles.blue}>Kandidato redagavimas</h2>
                <div style={styles.line}></div>

                <label>Vardas:</label><br />
                <input className="form-control" type="text" value={this.props.candidate.name}
                       onChange={this.props.onFieldChange('name')}/><br />
                <label>Pavardė</label><br />
                <input className="form-control" type="text" value={this.props.candidate.last_name}
                       onChange={this.props.onFieldChange('last_name')}/><br />
                <label>Gimimo data:</label><br />
                <input className="form-control" type="date" value={this.props.candidate.date_of_birth}
                       onChange={this.props.onFieldChange('date_of_birth')}/><br />
                <label>Aprašymas</label><br />
                <input className="form-control" type="text" value={this.props.candidate.description}
                       onChange={this.props.onFieldChange('description')}/><br />

                <button id="EditCandidateSave" className="btn btn-success" style={{marginRight: '20px'}}
                        onClick={this.props.onSaveClick}>Išsaugoti
                </button>
                <button id="EditCandidateCancel" className="btn btn-success" style={{marginRight: '20px'}}
                        onClick={this.props.onCancelClick}>Atšaukti
                </button>
            </form>
        )
    }
});

EditCandidateComponent.propTypes = {
    candidate: React.PropTypes.object.isRequired,
    onFieldChange: React.PropTypes.func.isRequired,
    onSaveClick: React.PropTypes.func.isRequired
};

window.EditCandidateComponent = EditCandidateComponent;